package io.github.yabs.yabsmobile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.yabs.yabsmobile.scanner.IntentIntegrator
import io.github.yabs.yabsmobile.scanner.IntentResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.offers_list.*
import kotlinx.android.synthetic.main.progress.*
import kotlinx.android.synthetic.main.retailer_details.*
import kotlinx.android.synthetic.main.retailers_detail_top.*
import kotlinx.android.synthetic.main.toolbar.*


class RetailerDetails : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }
    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_details)
        setSupportActionBar(toolbar_top)
        retailerCoinsTextView.text = retailer.balance.toString()
        customizeForRetailer(retailer.name, retailerDetailsGradientLayout)
        scanReceiptButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
        sellPointsButton.setOnClickListener {
            YouAreSellingActivity.start(this, retailer)
        }
        buyPointsButton.setOnClickListener {
            YouAreBuyingActivity.start(this, retailer)
        }
        claimPromoButton.setOnClickListener {
            PromoCodeActivity.start(this, retailer)
        }
    }

    override fun onResume() {
        super.onResume()
        yabsAmountText.reload()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val scanResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (scanResult != null) {
                // handle scan result
                Log.e("kasper", "toString() returns: " + scanResult.toString())
                disposable.add(Companion.claimPointsApi.claim(walletManager.getWallet().address, retailer.publicKey, scanResult.contents)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(IoScheduler())
                        .bindLoader(progressBar)
                        .subscribe({
                            Snackbar.make(retailerDetailsScreen, "Receipt has been sent", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Refresh", { finish() })
                                    .show()
                        }, {
                            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                        }))
            } else {
                // else continue with any other code you need in the method
                Log.e("kasper", "scanResult is null.")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        yabsAmountText.stopLoading()
        disposable.clear()
        super.onDestroy()
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(Intent(context, RetailerDetails::class.java)
                    .putExtra(RETAILER_KEY, retailer))
        }

        private const val RETAILER_KEY = "retailer"
        lateinit var walletManager: WalletManager
        lateinit var yabContractService: YabContractService
        lateinit var claimPointsApi: ClaimPointsApi
    }
}
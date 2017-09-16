package io.github.yabs.yabsmobile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.yabs.yabsmobile.scanner.IntentIntegrator
import io.github.yabs.yabsmobile.scanner.IntentResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.retailer_details.*
import java.math.BigInteger
import kotlinx.android.synthetic.main.retailers_detail_top.*


class RetailerDetails : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }
    private val yabsAmount by lazy { intent.getSerializableExtra(YABS_KEY) as BigInteger }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_details)
        setRetailerName(retailer.name)
        retailerCoinsTextView.text = retailer.balance
        yabsAmountText.text = "You have $yabsAmount yabs"
        scanReceiptButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
        claimPromoButton.setOnClickListener {
            PromoCodeActivity.start(this, retailer, yabsAmount)
        }
        sellPointsButton.setOnClickListener {
            YouAreSellingActivity.start(this, retailer, yabsAmount)
        }
        buyPointsButton.setOnClickListener {
            YouAreBuyingActivity.start(this, retailer, yabsAmount)
        }
    }

    fun setRetailerName(name: String) {
        when (name) {
            "Coop" -> retailerImageView.setImageResource(R.drawable.coop)
            "Fashwell" -> retailerImageView.setImageResource(R.drawable.fashwell)
            "Siroop" -> retailerImageView.setImageResource(R.drawable.siroop)
            "Zalando" -> retailerImageView.setImageResource(R.drawable.zalando)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val scanResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (scanResult != null) {
                // handle scan result
                Log.e("kasper", "toString() returns: " + scanResult.toString())
                disposable = Companion.claimPointsApi.claim(walletManager.getWallet().address, retailer.publicKey, scanResult.contents)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(IoScheduler())
                        .bindLoader(progressBar)
                        .subscribe({
                            Toast.makeText(this, "Wyslano", Toast.LENGTH_LONG).show()
                        }, {
                            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                        })
            } else {
                // else continue with any other code you need in the method
                Log.e("kasper", "scanResult is null.")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        fun start(context: Context, retailer: Retailer, yabsAmount: BigInteger) {
            context.startActivity(Intent(context, RetailerDetails::class.java)
                    .putExtra(RETAILER_KEY, retailer)
                    .putExtra(YABS_KEY, yabsAmount))
        }

        private const val RETAILER_KEY = "retailer"
        private const val YABS_KEY = "amount"
        lateinit var walletManager: WalletManager
        lateinit var claimPointsApi: ClaimPointsApi
    }
}
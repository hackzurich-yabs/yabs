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
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256


class RetailerDetails : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_details)
        retailerName.text = retailer.name
        retailerCoinsTextView.text = retailer.balance
        scanReceiptButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
        claimPromoButton.setOnClickListener {
            val points = Uint256(200)
            disposable = yabContractService
                    .executeRx { claimPromoCode(Address(retailer.publicKey), points) }
                    .flatMap {
                        claimPromoApi.claim(walletManager.getWallet().address, retailer.publicKey, it.transactionHash, points.value)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(IoScheduler())
                    .subscribe({
                        Toast.makeText(this, "PromoCode : $it", Toast.LENGTH_LONG).show()
                    }, {
                        Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                        Log.e("kasper", "$it")
                    })
        }
        sellPointsButton.setOnClickListener {
            SellOffersActivity.start(this, retailer)
        }
        buyPointsButton.setOnClickListener {
            BuyOffersActivity.start(this, retailer)
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
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(Intent(context, RetailerDetails::class.java).putExtra(RETAILER_KEY, retailer))
        }

        private const val RETAILER_KEY = "retailer"
        lateinit var walletManager: WalletManager
        lateinit var yabContractService: YabContractService
        lateinit var claimPointsApi: ClaimPointsApi
        lateinit var claimPromoApi: ClaimPromoCodeApi
    }
}
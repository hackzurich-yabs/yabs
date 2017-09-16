package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.yabs.yabsmobile.scanner.IntentIntegrator
import io.github.yabs.yabsmobile.scanner.IntentResult
import io.github.yabs.yabsmobile.solidity.YabsContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.retailer_details.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.Contract
import org.web3j.tx.ManagedTransaction


class RetailerDetails : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }
    private val web3 = JsonRpc2_0Web3j(InfuraHttpService("https://morden.infura.io/your-token"))

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_details)
        retailerName.text = retailer.name
        addReceiptButton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
        claimPromoButton.setOnClickListener {
            val points = Uint256(200)
            disposable = contractApi.address()
                    .flatMap {
                        val yabsContract = YabsContract.load(it, web3, walletManager.getWallet(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT)
                        Observable.fromFuture(yabsContract.claimPromoCode(Address(retailer.publicKey), points))
                    }
                    .flatMap {
                        claimPromoApi.claim(walletManager.getWallet().ecKeyPair.publicKey.toString(), retailer.publicKey, it.transactionHash, points.value)
                    }
                    .subscribe({
                        Toast.makeText(this, "PromoCode : $it", Toast.LENGTH_LONG).show()
                    }, {
                        Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            val scanResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (scanResult != null) {
                // handle scan result
                Log.e("kasper", "toString() returns: " + scanResult.toString())
                disposable = Companion.claimPointsApi.claim(walletManager.getWallet().ecKeyPair.publicKey.toString(), retailer.publicKey, scanResult.contents)
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
        lateinit var contractApi: ContractAdressApi
        lateinit var claimPointsApi: ClaimPointsApi
        lateinit var claimPromoApi: ClaimPromoCodeApi
    }
}
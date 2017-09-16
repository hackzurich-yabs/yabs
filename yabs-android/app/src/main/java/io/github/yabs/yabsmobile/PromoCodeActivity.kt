package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.promo_codes_list.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256

class PromoCodeActivity : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.promo_codes_list)
        retailerCoinsTextView.text = retailer.balance

        claimPromoCodeButton.setOnClickListener {
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
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(Intent(context, PromoCodeActivity::class.java)
                    .putExtra(RETAILER_KEY, retailer))
        }

        private const val RETAILER_KEY = "retailer"

        lateinit var yabContractService: YabContractService
        lateinit var claimPromoApi: ClaimPromoCodeApi
        lateinit var walletManager: WalletManager
    }
}
package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.progress.*
import kotlinx.android.synthetic.main.promo_code_field.view.*
import kotlinx.android.synthetic.main.promo_codes_list.*
import kotlinx.android.synthetic.main.retailers_detail_top.*
import kotlinx.android.synthetic.main.toolbar.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256

class PromoCodeActivity : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.promo_codes_list)
        setSupportActionBar(toolbar_top)
        customizeForRetailer(retailer.name, promoCodeBackgroundView)
        retailerCoinsTextView.text = retailer.balance.toString()
        yourPromoCodes.text = "Your ${retailer.name} promo codes"
        promoCodeListView.layoutManager = LinearLayoutManager(this)
        if (retailer.balance >= retailer.promoCodePrice) {
            claimPromoCodeButton.text = "Claim promocode!"
            claimPromoCodeButton.setOnClickListener {
                val points = Uint256(retailer.promoCodePrice)
                disposable = yabContractService
                        .executeRx { claimPromoCode(Address(retailer.publicKey), points) }
                        .flatMap {
                            claimPromoApi.claim(walletManager.getWallet().address, retailer.publicKey, it.transactionHash, points.value)
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(IoScheduler())
                        .bindLoader(progressBar)
                        .subscribe({
                            callForPromoCodes()
                            retailerCoinsTextView.text = (retailer.balance - retailer.promoCodePrice).toString()
                        }, {
                            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
                            Log.e("kasper", "$it")
                        })
            }
        } else {
            claimPromoCodeButton.text = "Insufficient funds"
            claimPromoCodeButton.setOnClickListener { }
        }
    }

    override fun onResume() {
        super.onResume()
        callForPromoCodes()
        yabsAmountText.reload()
    }

    private fun callForPromoCodes() {
        disposable?.dispose()
        disposable = claimPromoApi.getPromoCodes(walletManager.getWallet().address, retailer.publicKey)
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .bindLoader(progressBar)
                .subscribe({
                    promoCodeListView.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.promo_code_field) { holder, item ->
                        holder.itemView.promoCodeTextView.text = item
                    }
                }, {
                    Log.e("kasper", "$it")
                })
    }

    override fun onDestroy() {
        disposable?.dispose()
        yabsAmountText.stopLoading()
        super.onDestroy()
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
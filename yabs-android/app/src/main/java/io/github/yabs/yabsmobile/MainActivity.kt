package io.github.yabs.yabsmobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.github.yabs.yabsmobile.solidity.YabsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_retailer_field.view.*
import kotlinx.android.synthetic.main.progress.*
import kotlinx.android.synthetic.main.retailers_detail_top.*
import kotlinx.android.synthetic.main.toolbar.*
import org.web3j.abi.datatypes.Address

class MainActivity : AppCompatActivity() {

    private val retailersApi by lazy { BalancesApi.INSTANCE }
    private var disposable: Disposable? = null
    private val wallet by lazy { walletManager.getWallet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retailerListView.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(toolbar_top)
    }

    override fun onResume() {
        super.onResume()
        disposable?.dispose()
        yabsAmountText.reload()
        disposable = retailersApi.balances(wallet.address)
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .bindLoader(progressBar)
                .subscribe({
                    retailerListView.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.main_retailer_field) { holder, item ->
                        holder.itemView.retailerImageView.setImageResource(setImageResource(item.name))
                        holder.itemView.pointsCountTextView.text = item.balance
                        holder.itemView.setOnClickListener { _ ->
                            RetailerDetails.start(this, item)
                        }
                        holder.itemView.retailerItemLayout.setBackgroundResource(setGradientResource(item.name))
                    }
                }, {
                    Log.e("kasper", "$it")
                })
    }

    fun setImageResource(name: String) : Int {
        when(name) {
            "Coop" -> return R.drawable.coop
            "Swiss" -> return R.drawable.swiss
            "Siroop" -> return R.drawable.siroop
            "Zalando" -> return R.drawable.zalando
            else -> throw RuntimeException()
        }
    }

    fun setGradientResource(name: String) : Int {
        when(name) {
            "Coop" -> return R.drawable.coop_item_gradient
            "Swiss" -> return R.drawable.swiss_item_gradient
            "Siroop" -> return R.drawable.siroop_item_gradient
            "Zalando" -> return R.drawable.zalando_item_gradient
            else -> throw RuntimeException()
        }
    }

    private fun YabsContract.getYabs() = getBalance(Address(wallet.address), Address("0x0"))

    override fun onDestroy() {
        disposable?.dispose()
        yabsAmountText.stopLoading()
        super.onDestroy()
    }

    companion object {
        lateinit var walletManager: WalletManager
        lateinit var yabsService: YabContractService
    }
}

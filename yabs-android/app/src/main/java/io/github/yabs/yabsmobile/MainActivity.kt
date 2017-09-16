package io.github.yabs.yabsmobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.retailer_item.view.*

class MainActivity : AppCompatActivity() {

    private val retailersApi by lazy { BalancesApi.INSTANCE }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wallet = walletManager.getWallet()
        retailerListView.layoutManager = LinearLayoutManager(this)
        disposable = retailersApi.balances(wallet.ecKeyPair.publicKey.toString())
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    retailerListView.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.retailer_item) { holder, item ->
                        holder.itemView.retailerName.text = item.name
                        holder.itemView.retailerCoinsCount.text = item.balance
                        holder.itemView.setOnClickListener {
                            RetailerDetails.start(this, item)
                        }
                    }
                }, {
                    Log.e("kasper", "$it")
                })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        lateinit var walletManager: WalletManager
    }
}

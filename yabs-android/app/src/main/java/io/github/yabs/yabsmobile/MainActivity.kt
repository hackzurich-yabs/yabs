package io.github.yabs.yabsmobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.github.yabs.yabsmobile.solidity.YabsContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_retailer_field.view.*
import kotlinx.android.synthetic.main.progress.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256

class MainActivity : AppCompatActivity() {

    private val retailersApi by lazy { BalancesApi.INSTANCE }
    private var disposable: Disposable? = null
    private val wallet by lazy { walletManager.getWallet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retailerListView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        disposable?.dispose()
        disposable = Observable.zip<List<Retailer>, Uint256, Pair<List<Retailer>, Uint256>>(retailersApi.balances(wallet.address), yabsService.executeRx { getYabs() }, BiFunction { a, b -> a to b })
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .bindLoader(progressBar)
                .subscribe({
                    yabsAmount.text = "You have ${it.second.value} yabs"
                    retailerListView.adapter = basicAdapterWithLayoutAndBinder(it.first, R.layout.main_retailer_field) { holder, item ->
                        holder.itemView.retailerNameTextView.text = item.name
                        holder.itemView.pointsCountTextView.text = item.balance
                        holder.itemView.setOnClickListener { _ ->
                            RetailerDetails.start(this, item, it.second.value)
                        }
                    }
                }, {
                    Log.e("kasper", "$it")
                })
    }

    private fun YabsContract.getYabs() = getBalance(Address(wallet.address), Address("0x0"))

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        lateinit var walletManager: WalletManager
        lateinit var yabsService: YabContractService
    }
}

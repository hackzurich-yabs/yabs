package io.github.yabs.yabsmobile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.retailer_item.view.*
import org.web3j.crypto.WalletUtils
import java.io.File

class MainActivity : AppCompatActivity() {

    private var isUserCreated by sharedPrefsProvider<Boolean>().asProperty("isUserCreated")
    private val retailersApi by lazy { RetailersApi.INSTANCE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isUserCreated != null) {
            WalletUtils.generateFullNewWalletFile("password", File(filesDir, "credentials"))
            isUserCreated = true
        }
        retailerListView.layoutManager = LinearLayoutManager(this)
        retailersApi.call()
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    retailerListView.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.retailer_item) { holder, item ->
                        holder.itemView.retailerName.text = item.name
                        holder.itemView.retailerCoinsCount.text = item.coins
                        holder.itemView.setOnClickListener {
                            RetailerDetails.start(this, item)
                        }
                    }
                }, {
                    retailerListView.adapter = basicAdapterWithLayoutAndBinder(listOf(Retailer("biedron", "15")), R.layout.retailer_item) { holder, item ->
                        holder.itemView.retailerName.text = item.name
                        holder.itemView.retailerCoinsCount.text = item.coins
                        holder.itemView.setOnClickListener {
                            RetailerDetails.start(this, item)
                        }
                    }
                    Log.e("kasper", "$it")
                })
//            val integrator = IntentIntegrator(this)
//            integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (scanResult != null) {
                // handle scan result
                Log.e("kasper", "toString() returns: " + scanResult.toString());
            } else {
                // else continue with any other code you need in the method
                Log.e("kasper", "scanResult is null.")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

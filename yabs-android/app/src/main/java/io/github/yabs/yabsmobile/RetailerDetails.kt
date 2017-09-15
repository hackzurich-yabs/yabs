package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.retailer_details.*

class RetailerDetails : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_details)
        retailerName.text = retailer.name
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(Intent(context, RetailerDetails::class.java).putExtra(RETAILER_KEY, retailer))
        }

        private const val RETAILER_KEY = "retailer"
    }
}
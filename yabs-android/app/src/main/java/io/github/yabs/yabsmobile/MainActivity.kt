package io.github.yabs.yabsmobile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.web3j.crypto.WalletUtils
import java.io.File


class MainActivity : AppCompatActivity() {

    private var isUserCreated by sharedPrefsProvider<Boolean>().asProperty("isUserCreated")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isUserCreated != null) {
            WalletUtils.generateFullNewWalletFile("password", File(filesDir, "credentials"))
            isUserCreated = true
        }
        hello.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        }
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

package io.github.yabs.yabsmobile

import android.app.Activity
import android.view.View
import kotlinx.android.synthetic.main.retailers_detail_top.*

fun Activity.customizeForRetailer(name: String, backgroundLayout: View) {
    when (name) {
        "Coop" -> customizeRetailerLayout(R.drawable.coop, R.drawable.coop_gradient, backgroundLayout)
        "Fashwell" -> customizeRetailerLayout(R.drawable.fashwell, R.drawable.fashwell_gradient, backgroundLayout)
        "Siroop" -> customizeRetailerLayout(R.drawable.siroop, R.drawable.siroop_gradient, backgroundLayout)
        "Zalando" -> customizeRetailerLayout(R.drawable.zalando, R.drawable.zalando_gradient, backgroundLayout)
    }
}

private fun Activity.customizeRetailerLayout(image: Int, gradient: Int, backgroundLayout: View) {
    retailerImageView.setImageResource(image)
    backgroundLayout.setBackgroundResource(gradient)
}
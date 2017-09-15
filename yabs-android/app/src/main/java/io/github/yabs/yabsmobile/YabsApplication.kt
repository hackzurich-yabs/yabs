package io.github.yabs.yabsmobile

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.elpassion.android.commons.sharedpreferences.createSharedPrefs
import com.elpassion.sharedpreferences.gsonadapter.gsonConverterAdapter

class YabsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        contextProvider = { applicationContext }
    }
}

var contextProvider: () -> Context = { TODO() }

inline fun <reified T> sharedPrefsProvider() =
        createSharedPrefs({ PreferenceManager.getDefaultSharedPreferences(contextProvider()) }, gsonConverterAdapter<T>())
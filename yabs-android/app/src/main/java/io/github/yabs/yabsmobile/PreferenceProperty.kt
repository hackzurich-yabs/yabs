package io.github.yabs.yabsmobile

import com.elpassion.android.commons.sharedpreferences.SharedPreferenceRepository
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> SharedPreferenceRepository<T>.asProperty(key: String): ReadWriteProperty<Any, T?> = PreferencesProperty(this, key)

private class PreferencesProperty<T>(
        private val sharedPreferenceRepository: SharedPreferenceRepository<T>,
        private val key: String) : ReadWriteProperty<Any, T?> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = sharedPreferenceRepository.read(key)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        sharedPreferenceRepository.write(key, value)
    }
}
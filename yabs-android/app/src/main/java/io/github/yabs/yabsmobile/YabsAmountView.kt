package io.github.yabs.yabsmobile

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler

class YabsAmountView : TextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var dispose: Disposable? = null

    fun reload() {
        dispose?.dispose()
        dispose = yabsContractService.executeRx { wallet -> getYabs(wallet) }
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    text = "${it.value} yabs"
                }, {
                    Log.e("YabsAmount", "error while getting yabs: $it")
                })
    }

    fun stopLoading() {
        dispose?.dispose()
    }

    companion object {
        lateinit var yabsContractService: YabContractService
    }
}
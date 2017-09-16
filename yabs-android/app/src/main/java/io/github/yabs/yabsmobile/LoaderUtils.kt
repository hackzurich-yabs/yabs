package io.github.yabs.yabsmobile

import android.view.View
import com.elpassion.android.view.hide
import com.elpassion.android.view.show
import io.reactivex.Completable
import io.reactivex.Observable

fun Completable.bindLoader(progressBar: View): Completable {
    return doOnSubscribe { progressBar.show() }
            .doOnDispose { progressBar.hide() }
            .doOnComplete { progressBar.hide() }
}

fun <T> Observable<T>.bindLoader(progressBar: View): Observable<T> {
    return doOnSubscribe { progressBar.show() }
            .doOnDispose { progressBar.hide() }
            .doOnComplete { progressBar.hide() }
}

package com.germanofilho.app.core.repository

import android.util.Log
import io.reactivex.Observable

abstract class BaseRepository{

    fun <T> execute(observable: Observable<T>): Observable<T> {
        return observable
            .onErrorResumeNext { throwable: Throwable ->
                Log.e("Service", throwable.message)
                return@onErrorResumeNext observable
            }
    }

}
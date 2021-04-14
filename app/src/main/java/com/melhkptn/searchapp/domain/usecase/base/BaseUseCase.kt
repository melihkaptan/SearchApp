package com.melhkptn.searchapp.domain.usecase.base

import com.melhkptn.searchapp.domain.model.base.DataHolder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BaseUseCase {

    interface RequestInteractor<params : Params, T : Any> :
        BaseUseCase {

        fun fetch(compositeDisposable: CompositeDisposable,
                  postParams: params,
                  onResponse : (DataHolder<T>) -> Unit) : Disposable

        fun executeAsync(postParams: params): Single<DataHolder<T>>
    }

    interface Interactor<T : Any> :
        BaseUseCase {
        fun executeAsync(): Single<DataHolder<T>>
    }

    //marker class
    abstract class Params
}
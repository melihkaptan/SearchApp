package com.melhkptn.searchapp.domain.usecase

import com.melhkptn.searchapp.di.repository.SearchRepository
import com.melhkptn.searchapp.domain.usecase.base.BaseUseCase
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchRequest
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchDataUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseUseCase.RequestInteractor<SearchDataUseCase.Params, SearchResponse> {

    class Params(
        val term: String?,
        val offset: Int?,
        val limit: Int?,
        val entity: String?
    ) : BaseUseCase.Params()

    override fun fetch(
        compositeDisposable: CompositeDisposable,
        postParams: Params,
        onResponse: (DataHolder<SearchResponse>) -> Unit
    ): Disposable {
        return this.executeAsync(postParams)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse(it)
            },
                {
                    onResponse(
                        DataHolder.Fail(
                            Error(
                                it.message
                            )
                        )
                    )
                }
            )
    }

    override fun executeAsync(postParams: Params): Single<DataHolder<SearchResponse>> =
        searchRepository.fetchData(
            SearchRequest(
                term = postParams.term,
                offset = postParams.offset,
                limit = postParams.limit,
                entity = postParams.entity
            )
        )
}
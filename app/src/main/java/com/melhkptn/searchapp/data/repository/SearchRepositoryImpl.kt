package com.melhkptn.searchapp.data.repository

import com.melhkptn.searchapp.data.base.DataSource
import com.melhkptn.searchapp.di.repository.SearchRepository
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchRequest
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: DataSource.RequestRemoteDataSource<SearchRequest, SearchResponse>
) : SearchRepository {

    override fun fetchData(request: SearchRequest): Single<DataHolder<SearchResponse>> =
        searchRemoteDataSource.getResult(request)
            .onErrorReturn {
                DataHolder.Fail(Error(it.message))
            }

}
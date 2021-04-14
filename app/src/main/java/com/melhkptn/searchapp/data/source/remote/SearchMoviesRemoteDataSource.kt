package com.melhkptn.searchapp.data.source.remote

import com.melhkptn.searchapp.data.base.DataSource
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchRequest
import io.reactivex.Single
import javax.inject.Inject

class SearchMoviesRemoteDataSource @Inject constructor(
    private val api: SearchAPI
) : DataSource.RequestRemoteDataSource<SearchRequest, SearchResponse> {
    override fun getResult(request: SearchRequest): Single<DataHolder<SearchResponse>> =
        api.getMovies(
            request.term!!,
            request.offset!!,
            request.limit!!,
            request.entity!!
        ).map {
            return@map DataHolder.Success(it)
        }

}
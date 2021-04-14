package com.melhkptn.searchapp.data.repository

import com.melhkptn.searchapp.data.base.DataSource
import com.melhkptn.searchapp.di.repository.SearchRepository
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchMovieRequest
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchMovieRemoteDataSource: DataSource.RequestRemoteDataSource<SearchMovieRequest, SearchResponse>
) : SearchRepository {

    override fun fetchMovies(request: SearchMovieRequest): Single<DataHolder<SearchResponse>> =
        searchMovieRemoteDataSource.getResult(request)
            .onErrorReturn {
                DataHolder.Fail(Error(it.message))
            }

}
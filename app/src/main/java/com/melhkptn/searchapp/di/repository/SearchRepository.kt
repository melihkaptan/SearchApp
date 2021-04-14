package com.melhkptn.searchapp.di.repository

import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchMovieRequest
import io.reactivex.Single

interface SearchRepository {

    fun fetchMovies(
        request: SearchMovieRequest
    ): Single<DataHolder<SearchResponse>>
}
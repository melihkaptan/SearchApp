package com.melhkptn.searchapp.data.source.remote

import com.melhkptn.searchapp.domain.model.response.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("/search")
    fun getMovies(
        @Query("term") term: String,
        @Query("offset") offset: Int,
        @Query("limit") limit : Int,
        @Query("entity") entity: String
    ) : Single<SearchResponse>
}
package com.melhkptn.searchapp.domain.model.request

import com.google.gson.annotations.SerializedName

data class SearchMovieRequest(
    @SerializedName("term") var term: String? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("entity") var entity: String? = null
)
package com.melhkptn.searchapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("resultCount") val resultCount : Int,
    @SerializedName("results") val results : List<Results>
)



package com.melhkptn.searchapp.domain.paging

import androidx.paging.DataSource
import com.melhkptn.searchapp.domain.model.response.Results
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.usecase.SearchDataUseCase
import com.melhkptn.searchapp.domain.usecase.base.BaseUseCase
import com.melhkptn.searchapp.util.Entity

class SearchDataSourceFactory(
    private val searchQuery: String,
    private val entity: Entity,
    private val searchDataUseCase: BaseUseCase.RequestInteractor<SearchDataUseCase.Params, SearchResponse>
) : DataSource.Factory<Int, Results>() {
    override fun create(): DataSource<Int, Results> {
        return SearchPagingDataSource(searchQuery, entity, searchDataUseCase)
    }
}
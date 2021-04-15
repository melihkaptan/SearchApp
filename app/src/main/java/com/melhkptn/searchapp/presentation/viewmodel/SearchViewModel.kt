package com.melhkptn.searchapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.response.Results
import com.melhkptn.searchapp.domain.paging.SearchDataSourceFactory
import com.melhkptn.searchapp.domain.usecase.base.BaseUseCase
import com.melhkptn.searchapp.domain.usecase.SearchDataUseCase
import com.melhkptn.searchapp.util.Constants.PAGE_SIZE
import com.melhkptn.searchapp.util.Constants.PREFETCH_DISTANCE
import com.melhkptn.searchapp.util.Entity


class SearchViewModel @ViewModelInject constructor(
    private val searchDataUseCase: BaseUseCase.RequestInteractor<SearchDataUseCase.Params, SearchResponse>
) : BaseViewModel() {

    var searchQueryLiveData = MutableLiveData<String>()
    var entity = MutableLiveData<Entity>(Entity.MOVIES)
    val searchResultLiveData: LiveData<PagedList<Results>>

    init {
        val config = with(PagedList.Config.Builder()) {
            setPageSize(PAGE_SIZE)
            setInitialLoadSizeHint(PAGE_SIZE)
            setPrefetchDistance(PREFETCH_DISTANCE)
            build()
        }

        searchResultLiveData = Transformations.switchMap(searchQueryLiveData) { query ->
            val factory = SearchDataSourceFactory(
                query,
                entity.value!!,
                searchDataUseCase
            )
            LivePagedListBuilder(factory, config).build()
        }

    }


    fun searchQuery(query: String, entity: Entity?) {
        searchQueryLiveData.value = query
        this.entity.value = entity
    }

}
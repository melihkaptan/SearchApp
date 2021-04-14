package com.melhkptn.searchapp.domain.paging

import androidx.paging.PageKeyedDataSource
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.response.Results
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.usecase.SearchDataUseCase
import com.melhkptn.searchapp.domain.usecase.base.BaseUseCase
import com.melhkptn.searchapp.util.Constants.PAGE_SIZE
import com.melhkptn.searchapp.util.Entity
import io.reactivex.disposables.CompositeDisposable

class SearchPagingDataSource(
    private val searchQuery: String,
    private val entity: Entity,
    private val searchDataUseCase: BaseUseCase.RequestInteractor<SearchDataUseCase.Params, SearchResponse>
) : PageKeyedDataSource<Int, Results>() {

    private val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Results>
    ) {
        //first page
        searchDataUseCase.fetch(
            compositeDisposable, SearchDataUseCase.Params(
                searchQuery,
                1,//pageNum
                PAGE_SIZE,
                convertToSearchString(entity)
            )
        ) {
            when (it) {
                is DataHolder.Success -> {
                    callback.onResult(
                        it.data.results, null,
                        INITIAL_PAGE
                    )
                }
                is DataHolder.Fail -> println(it.e.localizedMessage)

            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {
        //next pages
        searchDataUseCase.fetch(
            compositeDisposable, SearchDataUseCase.Params(
                searchQuery,
                params.key,//pageNum
                PAGE_SIZE,
                convertToSearchString(entity)
            )
        ) {
            when (it) {
                is DataHolder.Success -> {
                    callback.onResult(it.data.results, params.key + 1)
                }
                is DataHolder.Fail -> println(it.e.localizedMessage)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {
    }

    private fun convertToSearchString(entity: Entity) : String{
        when(entity){
            Entity.MOVIES -> return "movie"
            Entity.MUSIC -> return "musicTrack"
            Entity.BOOKS -> return "ebook"
            Entity.APPS -> return "software"
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}
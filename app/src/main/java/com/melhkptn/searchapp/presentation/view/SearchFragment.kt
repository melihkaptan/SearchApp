package com.melhkptn.searchapp.presentation.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.recyclerview.widget.GridLayoutManager
import com.melhkptn.searchapp.R
import com.melhkptn.searchapp.domain.paging.MovieLoadStateAdapter
import com.melhkptn.searchapp.presentation.adapter.SearchAdapter
import com.melhkptn.searchapp.presentation.viewmodel.SearchViewModel
import com.melhkptn.searchapp.util.Entity
import com.melhkptn.searchapp.util.hide
import com.melhkptn.searchapp.util.hideKeyboard
import com.melhkptn.searchapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*


@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var adapter = SearchAdapter()
    override fun getLayoutRes() = R.layout.fragment_search
    var selectedSection = Entity.MOVIES

    override fun initView() {
        super.initView()
        viewModel.searchQuery(INITIAL_SEARCH_QUERY, Entity.MOVIES)

        initRecyclerView()
        initObservers()
        initHeaders()
        initSearchView()
        initProgressBar()

    }

    private fun initProgressBar(){
        // show the loading state for te first load
        adapter.addLoadStateListener { _, loadState: LoadState ->
            if (loadState is LoadState.Loading && adapter.itemCount == 0)
                progressBar.show()
            else {
                progressBar.hide()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter {}
        )

    }

    private fun initObservers() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    private fun initSearchView() {
        searchView?.apply {
            maxWidth = Integer.MAX_VALUE
            queryHint = resources.getString(R.string.search_title)
            imeOptions = imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {

                    if (query.length > 2)
                        viewModel.searchQuery(query, selectedSection)
                    else
                        viewModel.searchQuery("", selectedSection)

                    return true
                }
            })
        }
    }

    private fun initHeaders() {

        segmentedControlGroup.setOnSelectedOptionChangeCallback {
            when (it) {
                Entity.MOVIES.ordinal ->
                    selectedSection = Entity.MOVIES
                Entity.MUSIC.ordinal ->
                    selectedSection = Entity.MUSIC
                Entity.APPS.ordinal ->
                    selectedSection = Entity.APPS
                Entity.BOOKS.ordinal ->
                    selectedSection = Entity.BOOKS
            }

            viewModel.searchQuery(
                "",
                selectedSection
            )

            viewModel.searchQuery(
                searchView.query.toString(),
                selectedSection
            )

        }
    }


    companion object {
        const val INITIAL_SEARCH_QUERY = "harry"
    }

}
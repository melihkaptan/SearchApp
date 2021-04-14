package com.melhkptn.searchapp.presentation.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.melhkptn.searchapp.R
import com.melhkptn.searchapp.domain.model.base.DataHolder
import com.melhkptn.searchapp.domain.model.request.SearchMovieRequest
import com.melhkptn.searchapp.presentation.adapter.SearchAdapter
import com.melhkptn.searchapp.presentation.viewmodel.SearchViewModel
import com.melhkptn.searchapp.util.Entity
import com.melhkptn.searchapp.util.hide
import com.melhkptn.searchapp.util.hideKeyboard
import com.melhkptn.searchapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var adapter = SearchAdapter()
    override fun getLayoutRes() = R.layout.fragment_search
    var selectedSection = Entity.MOVIES


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.searchQuery(INITIAL_SEARCH_QUERY, Entity.MOVIES)
    }

    override fun initView() {
        super.initView()

        initRecyclerView()
        initObservers()


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

        initSearchView()

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

    }

    private fun initObservers() {
        viewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.loadingProgressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.show()
                recyclerView.hide()
            } else {
                progressBar.hide()
                recyclerView.show()
            }
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

    companion object {
        const val INITIAL_SEARCH_QUERY = "jack"
    }

}
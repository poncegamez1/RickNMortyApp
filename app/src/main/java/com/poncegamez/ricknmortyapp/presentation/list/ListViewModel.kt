package com.poncegamez.ricknmortyapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.paging.RickAndMortyPagingSource
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _searchListData = MutableLiveData<PagingData<Characters>>()
    //val searchListData: LiveData<PagingData<Characters>> = _searchListData

    private val searchQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickAndMortyPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    private val debouncedSearchQuery = searchQuery
        .debounce(300)
        .distinctUntilChanged()

    val searchListFlow = debouncedSearchQuery.flatMapLatest { query ->
        if (query.isNotEmpty()) {
            Pager(PagingConfig(pageSize = 1)) {
                RickAndMortyPagingSource(repository, query)
            }.flow.cachedIn(viewModelScope)
        } else {
            emptyFlow()
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun cancelSearchJob() {
        searchJob?.cancel()
    }

    fun searchCharacters() {
        searchJob = viewModelScope.launch {
            val query = searchQuery.value
            val result = if (query.isNotEmpty()) {
                val pagingSource = RickAndMortyPagingSource(repository, query)
                Pager(PagingConfig(pageSize = 1)) {
                    pagingSource
                }.flow
                    .cachedIn(viewModelScope)
                    .map { pagingData ->
                        pagingData
                    }
            } else {
                flowOf(PagingData.empty())
            }
            result.collectLatest { pagingData ->
                _searchListData.value = pagingData
            }
        }
    }


}
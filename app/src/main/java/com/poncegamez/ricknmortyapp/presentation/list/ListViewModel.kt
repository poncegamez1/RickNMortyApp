package com.poncegamez.ricknmortyapp.presentation.list

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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Characters>>(PagingData.empty())
    private val searchQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    private val debouncedSearchQuery = searchQuery
        .debounce(500)
        .distinctUntilChanged()

    val searchListFlow = debouncedSearchQuery.flatMapLatest { query ->
            Pager(PagingConfig(pageSize = 1)) {
                RickAndMortyPagingSource(repository, query)
            }.flow.cachedIn(viewModelScope)
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun searchCharacters() {
        searchJob = viewModelScope.launch {
            val query = searchQuery.value
            if (query.isNotEmpty()) {
                _searchResults.value = PagingData.empty()
                searchListFlow.collectLatest { pagingData ->
                    _searchResults.value = pagingData
                }
            } else {
                _searchResults.value = PagingData.empty()
            }
        }
    }
}
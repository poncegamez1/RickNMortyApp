package com.poncegamez.ricknmortyapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.poncegamez.ricknmortyapp.paging.RickAndMortyPagingSource
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickAndMortyPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}
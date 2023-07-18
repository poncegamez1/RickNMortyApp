package com.poncegamez.ricknmortyapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import java.lang.Exception

class RickAndMortyPagingSource(private val repository: RickAndMortyRepository, private val query: String? = null) :
    PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        return try {
            val currentPage = params.key ?: 1

            val response = if (query.isNullOrEmpty()) {
                repository.getCharactersList(currentPage)
            } else {
                repository.searchCharacters(query, currentPage)
            }

            when (response) {
                is Results.Success -> {
                    val data = response.data ?: emptyList()
                    val responseData = mutableListOf<Characters>()
                    responseData.addAll(data)

                    LoadResult.Page(
                        data = responseData,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = currentPage + 1
                    )
                }
                is Results.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Results.Loading -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = currentPage + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}
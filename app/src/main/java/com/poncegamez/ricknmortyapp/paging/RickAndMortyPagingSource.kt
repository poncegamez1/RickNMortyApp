package com.poncegamez.ricknmortyapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import java.lang.Exception
import javax.inject.Inject

private const val RNM_STARTING_PAGE_INDEX = 1

class RickAndMortyPagingSource @Inject constructor(private val repository: RickAndMortyRepository, private val query: String? = null) :
    PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        return try {
            val currentPage = params.key ?: RNM_STARTING_PAGE_INDEX

            when (val response = repository.searchCharacters(query, currentPage)) {
                is Results.Success -> {
                    val data = response.data ?: emptyList()
                    val responseData = mutableListOf<Characters>()
                    responseData.addAll(data)

                    LoadResult.Page(
                        data = responseData,
                        prevKey = if (currentPage == RNM_STARTING_PAGE_INDEX) null else currentPage - 1,
                        nextKey = currentPage + 1
                    )
                }
                is Results.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Results.Loading -> {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = if (currentPage == RNM_STARTING_PAGE_INDEX) null else currentPage - 1,
                        nextKey = currentPage + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
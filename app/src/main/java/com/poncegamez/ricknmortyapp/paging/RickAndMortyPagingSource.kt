package com.poncegamez.ricknmortyapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.first
import java.lang.Exception

class RickAndMortyPagingSource(private val repository: RickAndMortyRepository) :
    PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        return try {
            val currentPage = params.key ?: 1

            when (val response = repository.getCharactersList(currentPage).first()) {
                is Results.Success<*> -> {
                    val responseData = response.data ?: emptyList()

                    LoadResult.Page(
                        data = responseData,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = currentPage.plus(1)
                    )
                }
                is Results.Error<*> -> {
                    LoadResult.Error(Exception(response.message))
                }
                else -> {
                    LoadResult.Error(Exception("Unknown error occurred"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
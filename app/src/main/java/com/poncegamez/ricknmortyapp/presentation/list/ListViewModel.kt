package com.poncegamez.ricknmortyapp.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private var charactersState: MutableLiveData<List<Characters>?> =
        MutableLiveData<List<Characters>?>()
    val onCharactersState: MutableLiveData<List<Characters>?> get() = charactersState

    fun getCharactersFromServer() {
        viewModelScope.launch(coroutineContext) {
            repository.getCharactersList(1).collect { results ->
                if (results is Results.Success) {
                    charactersState.postValue(results.data)
                }
            }
        }
    }
}
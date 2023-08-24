package com.poncegamez.ricknmortyapp.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {
    private var detailState: MutableLiveData<CharacterDetail?> = MutableLiveData<CharacterDetail?>()
    val onDetailState: MutableLiveData<CharacterDetail?> get() = detailState
    val isLoading = MutableLiveData<Boolean>()

    fun getDetailFromServer(characterId: Int){
        viewModelScope.launch(coroutineContext){
            isLoading.postValue(true)
            val response = repository.getCharacterDetail(characterId)
            if (response is Results.Success && response.data != null) {
                detailState.postValue(response.data)
                isLoading.postValue(false)
            }
        }
    }
}
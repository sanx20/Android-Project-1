package com.group18.androidproject1.ui.viewmodels.anime
import androidx.lifecycle.*
import com.group18.androidproject1.data.models.Anime
import com.group18.androidproject1.data.repository.anime.AnimeRepository
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _animeList = MutableLiveData<List<Anime>>()
    val animeList: LiveData<List<Anime>> get() = _animeList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage

    private var currentPage = 1

    private var _isFetching = false

    init {
        fetchAnimes()
    }

    fun fetchAnimes() {
        if (_isFetching) return

        _isFetching = true
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTopAnimes(currentPage)
            result.fold(onSuccess = { response ->
                val currentList = _animeList.value ?: emptyList()
                _animeList.value = currentList + response.data // Append new data
                _hasNextPage.value = response.pagination.hasNextPage
            }, onFailure = {
                _errorMessage.value = it.message
            })
            _isLoading.value = false
            _isFetching = false
        }
    }


    fun loadNextPage() {
        if (_hasNextPage.value == true && !_isFetching) {
            currentPage++
            fetchAnimes()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

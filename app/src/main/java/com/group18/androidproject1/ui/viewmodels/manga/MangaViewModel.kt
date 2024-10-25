package com.group18.androidproject1.ui.viewmodels.manga

import androidx.lifecycle.*
import com.group18.androidproject1.data.models.Manga
import com.group18.androidproject1.data.repository.manga.MangaRepository
import kotlinx.coroutines.launch

class MangaViewModel(private val repository: MangaRepository) : ViewModel() {

    private val _mangaList = MutableLiveData<List<Manga>>()
    val mangaList: LiveData<List<Manga>> get() = _mangaList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage

    private var currentPage = 1
    private var _isFetching = false

    init {
        fetchManga()
    }

    fun fetchManga() {
        if (_isFetching) return

        _isFetching = true
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTopManga(currentPage)
            result.fold(onSuccess = {
                val currentList = _mangaList.value ?: emptyList()
                _mangaList.value = currentList + it.data
                _hasNextPage.value = it.pagination.hasNextPage
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
            fetchManga()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

package com.group18.androidproject1.ui.viewmodels.review

import androidx.lifecycle.*
import com.group18.androidproject1.data.models.Review
import com.group18.androidproject1.data.repository.review.ReviewRepository
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> get() = _reviewList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> get() = _hasNextPage

    private var currentPage = 1

    init {
        fetchReviews()
    }

    fun fetchReviews() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTopReviews(currentPage)
            result.fold(onSuccess = { response ->
                _reviewList.value = response.data
                _hasNextPage.value = response.pagination.hasNextPage
            }, onFailure = {
                _errorMessage.value = it.message
            })
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        if (_hasNextPage.value == true) {
            currentPage++
            fetchReviews()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

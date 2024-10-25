package com.group18.androidproject1.ui.viewmodels.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group18.androidproject1.data.repository.review.ReviewRepository

class ReviewViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.group18.androidproject1.ui.viewmodels.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group18.androidproject1.data.repository.anime.AnimeRepository

class AnimeViewModelFactory(private val repository: AnimeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            return AnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

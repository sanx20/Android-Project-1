package com.group18.androidproject1.ui.viewmodels.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group18.androidproject1.data.repository.manga.MangaRepository

class MangaViewModelFactory(private val repository: MangaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MangaViewModel::class.java)) {
            return MangaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

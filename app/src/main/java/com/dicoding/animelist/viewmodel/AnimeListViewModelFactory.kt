package com.dicoding.animelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.dicoding.animelist.di.AnimeInjection
import com.dicoding.animelist.repository.AnimeRepository

class AnimeViewModelFactory private constructor(private val animeRepository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((AnimeListViewModel::class.java))) {
            return AnimeListViewModel(animeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: AnimeViewModelFactory? = null
        fun getInstance(context: Context): AnimeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AnimeViewModelFactory(AnimeInjection.provideRepository(context))
            }.also { instance = it }
    }
}


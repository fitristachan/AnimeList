package com.dicoding.animelist.di

import android.content.Context
import com.dicoding.animelist.database.AnimeDatabase
import com.dicoding.animelist.repository.AnimeRepository

object AnimeInjection {
    fun provideRepository(context: Context): AnimeRepository {
        val database = AnimeDatabase.getDatabase(context)
        val dao = database.animeDao()
        return AnimeRepository.getInstance(dao)
    }
}
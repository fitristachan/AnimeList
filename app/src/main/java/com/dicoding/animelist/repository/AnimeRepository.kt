package com.dicoding.animelist.repository

import com.dicoding.animelist.database.AnimeDao
import com.dicoding.animelist.database.AnimeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeRepository private constructor(private val animeDao: AnimeDao) {

    fun addAnime(animeEntity: List<AnimeEntity>) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            animeDao.deleteAnimeByTitle(animeEntity.first().title)
            animeDao.addAnime(animeEntity)
        }
    }

    fun deleteAnimeByTitle(title: String) {
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            animeDao.deleteAnimeByTitle(title)
        }
    }

    suspend fun getAllAnime(): List<AnimeEntity> {
        return withContext(Dispatchers.IO) {
            animeDao.getAllAnime()
        }
    }

    suspend fun getAnimeByTitle(title: String): List<AnimeEntity> {
        return withContext(Dispatchers.IO) {
            animeDao.getAllAnimeByTitle(title)
        }
    }

    suspend fun searchAnime(query: String): List<AnimeEntity>{
        return withContext(Dispatchers.IO) {
            animeDao.getAllAnime().filter {
                it.title.contains(query, ignoreCase = true)
            }
        }

    }


    companion object {
        @Volatile
        private var instance: AnimeRepository? = null
        fun getInstance(
            animeDao: AnimeDao,
        ): AnimeRepository =
            instance ?: synchronized(this) {
                instance ?: AnimeRepository(animeDao)
            }.also { instance = it }
    }
}
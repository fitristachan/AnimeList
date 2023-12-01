package com.dicoding.animelist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.animelist.database.AnimeEntity
import com.dicoding.animelist.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeListViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    fun addAnime(animeEntity: List<AnimeEntity>) = animeRepository.addAnime(animeEntity)

    fun deleteAnime(title: String) = animeRepository.deleteAnimeByTitle(title)

    private val _animeList = MutableStateFlow<List<AnimeEntity>>(emptyList())

    val getAllAnime: StateFlow<List<AnimeEntity>> = _animeList

    suspend fun loadAnimeList() {
        val animeList = animeRepository.getAllAnime().sortedBy { it.title }
        _animeList.value = animeList
    }

    val getAnimeByTitle: StateFlow<List<AnimeEntity>> = _animeList

    suspend fun loadAnimeByTitle(title: String) {
        val animeByTitle = animeRepository.getAnimeByTitle(title).sortedBy { it.title }
        _animeList.value = animeByTitle
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun loadSearchAnime(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            val animeList = animeRepository.searchAnime(_query.value)
                .sortedBy { it.title }
            _animeList.value = animeList
        }
    }



}



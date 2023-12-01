package com.dicoding.animelist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAnime(animeEntity: List<AnimeEntity>)

    @Query("DELETE FROM anime WHERE title = :title")
    fun deleteAnimeByTitle(title: String)

    @Query("SELECT * FROM anime")
    fun getAllAnime(): List<AnimeEntity>

    @Query("SELECT * FROM anime WHERE title = :title")
    fun getAllAnimeByTitle(title: String): List<AnimeEntity>
}
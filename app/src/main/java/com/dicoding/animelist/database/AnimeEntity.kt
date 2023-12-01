package com.dicoding.animelist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "photo")
    val photo: String,

    @ColumnInfo(name = "desc")
    val desc: String,

    @ColumnInfo(name = "airingDate")
    val airingDate: String,

    @ColumnInfo(name = "episode")
    val episode: String
)
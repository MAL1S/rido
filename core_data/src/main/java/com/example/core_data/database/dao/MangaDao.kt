package com.example.core_data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.core_domain.model.comics.manga.LocalMangaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Query("SELECT * FROM Manga")
    suspend fun getManga(): Flow<List<LocalMangaItem>>
}

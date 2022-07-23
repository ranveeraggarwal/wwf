package com.walagran.wwf.storage.daos

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.walagran.wwf.storage.entities.Dictionary

@Dao
interface DictionaryDao {
    @Nullable
    @Query("SELECT * FROM dictionary_table WHERE word == :word LIMIT 1")
    suspend fun findWord(word: String): Dictionary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Dictionary)

    @Query("DELETE FROM dictionary_table")
    suspend fun deleteAll(): Int
}
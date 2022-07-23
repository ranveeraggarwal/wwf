package com.walagran.wwf.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_table")
class Dictionary(
    @PrimaryKey @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "used_by_random") var usedByRandom: Boolean = false,
    @ColumnInfo(name = "times_created") var timesCreated: Int = 0,
)
package com.walagran.wwf.storage.repositories

import androidx.annotation.WorkerThread
import com.walagran.wwf.storage.daos.DictionaryDao
import com.walagran.wwf.storage.entities.Dictionary

class DictionaryRepository(private val dictionaryDao: DictionaryDao) {

    @WorkerThread
    suspend fun insert(word: String) {
        dictionaryDao.insertEntry(Dictionary(word))
    }

    fun isWordInDictionary(word: String): Boolean {
        return dictionaryDao.findWord(word) != null
    }
}
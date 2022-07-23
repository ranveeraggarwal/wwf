package com.walagran.wwf.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.walagran.wwf.storage.repositories.DictionaryRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DictionaryViewModel(private val repository: DictionaryRepository) :
    ViewModel() {

    fun isWordInDictionary(word: String): Boolean = runBlocking {
        return@runBlocking repository.isWordInDictionary(word)
    }

    fun insert(word: String) = viewModelScope.launch {
        repository.insert(word)
    }
}

class DictionaryViewModelFactory(private val repository: DictionaryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
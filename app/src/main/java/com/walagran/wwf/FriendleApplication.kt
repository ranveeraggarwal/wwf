package com.walagran.wwf

import android.app.Application
import com.walagran.wwf.storage.FriendleDatabase
import com.walagran.wwf.storage.repositories.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FriendleApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy {
        FriendleDatabase.getDatabase(this,
            applicationScope)
    }
    val repository by lazy { DictionaryRepository(database.dictionaryDao()) }
}
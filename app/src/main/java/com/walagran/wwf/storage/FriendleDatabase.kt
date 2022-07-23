package com.walagran.wwf.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.walagran.wwf.storage.daos.DictionaryDao
import com.walagran.wwf.storage.entities.Dictionary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Dictionary::class], version = 1, exportSchema = false)
abstract class FriendleDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    private class FriendleDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    val dictionaryDao = it.dictionaryDao()

                    dictionaryDao.deleteAll()

                    // ToDo: Add all the other entries.
                    dictionaryDao.insertEntry(Dictionary("FLASH"))
                    dictionaryDao.insertEntry(Dictionary("FLAGS"))
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FriendleDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): FriendleDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    FriendleDatabase::class.java,
                    "friendle_database")
                    .addCallback(FriendleDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
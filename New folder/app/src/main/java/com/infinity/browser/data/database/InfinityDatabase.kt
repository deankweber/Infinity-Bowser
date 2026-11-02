package com.infinity.browser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookmarkEntity::class, HistoryEntity::class, DownloadEntity::class], version = 1, exportSchema = false)
abstract class InfinityDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao
    abstract fun downloadDao(): DownloadDao

    companion object {
        @Volatile
        private var INSTANCE: InfinityDatabase? = null

        fun getDatabase(context: Context): InfinityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InfinityDatabase::class.java,
                    "infinity_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.example.memtone.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Contact::class,  Transaction::class],
    version = RVDatabase.version,
    exportSchema = false
)
abstract class RVDatabase: RoomDatabase() {
    abstract fun ContactDao(): ContactDao
    abstract fun TransactionDao(): TransactionDao

    companion object {
        private const val TAG = "RVDatabase"
        const val version = 2

        @Volatile
        private var instance: RVDatabase? = null

        fun getInstance(context: Context): RVDatabase=
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) : RVDatabase {
            return Room.databaseBuilder(
                context,
                RVDatabase::class.java,
                "RVDatabase"
            ).build()
        }

    }
}

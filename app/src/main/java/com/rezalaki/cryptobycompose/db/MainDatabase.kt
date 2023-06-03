package com.rezalaki.cryptobycompose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rezalaki.cryptobycompose.models.Crypto

@Database(
    version = 1,
    exportSchema = false,
    entities = [Crypto::class]
)
abstract class MainDatabase: RoomDatabase() {
    abstract fun mainDao(): MainDao
}
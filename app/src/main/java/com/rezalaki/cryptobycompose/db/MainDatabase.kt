package com.rezalaki.cryptobycompose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.rezalaki.cryptobycompose.models.Crypto

@Database(
    version = 1,
    exportSchema = false,
    entities = [Crypto::class]
)
@TypeConverters(
    DatabaseConverters::class
)
abstract class MainDatabase: RoomDatabase() {
    abstract fun mainDao(): MainDao
}
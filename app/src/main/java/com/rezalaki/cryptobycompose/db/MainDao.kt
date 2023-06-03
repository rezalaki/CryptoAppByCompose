package com.rezalaki.cryptobycompose.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    @Query("SELECT * FROM ${Constants.TABLE_CRYPTO}")
    suspend fun fetchAll(): Flow<List<Crypto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(cryptoList: List<Crypto>)
}
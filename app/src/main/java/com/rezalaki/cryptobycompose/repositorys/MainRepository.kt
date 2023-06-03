package com.rezalaki.cryptobycompose.repositorys

import androidx.room.Dao
import com.rezalaki.cryptobycompose.db.MainDao
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.network.Apis
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: Apis,
    private val db: MainDao
) {
    suspend fun callCryptoList(page: Int) = api.callCryptoList(page)

    fun fetchAll() = db.fetchAll()

    suspend fun saveCryptoList(cryptoList: List<Crypto>) = db.saveAll(cryptoList)
}
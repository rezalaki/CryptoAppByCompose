package com.rezalaki.cryptobycompose.repositorys

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Dao
import com.rezalaki.cryptobycompose.db.MainDao
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.network.ApiPagingSource
import com.rezalaki.cryptobycompose.network.Apis
import com.rezalaki.cryptobycompose.utils.Constants.API_PAGING_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: Apis,
    private val db: MainDao
) {
    fun callCryptoList() = Pager(
        config = PagingConfig(pageSize = API_PAGING_SIZE),
        pagingSourceFactory = {
            ApiPagingSource(api)
        }
    ).flow.flowOn(Dispatchers.IO)

    fun fetchAllFromDatabase() = db.fetchAll()

    suspend fun saveCryptoList(cryptoList: List<Crypto>) = db.saveAll(cryptoList)
}
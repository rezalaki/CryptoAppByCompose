package com.rezalaki.cryptobycompose.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.repositorys.MainRepository
import javax.inject.Inject

class ApiPagingSource @Inject constructor(
    private val api: Apis
) : PagingSource<Int, Crypto>() {
    override fun getRefreshKey(state: PagingState<Int, Crypto>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Crypto> {
        return try {
            val page = params.key ?: 1
            val response = api.callCryptoList(page)

            LoadResult.Page(
                data = response.body()!!,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.body()!!.isEmpty()) null else page.plus(1),
            )

        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

}
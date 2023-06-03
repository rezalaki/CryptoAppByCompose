package com.rezalaki.cryptobycompose.network

import com.rezalaki.cryptobycompose.models.Crypto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apis {

    @GET("markets?vs_currency=usd&order=market_cap_desc&sparkline=false")
    suspend fun callCryptoList(@Query("page") page: Int): Response<List<Crypto>>

}
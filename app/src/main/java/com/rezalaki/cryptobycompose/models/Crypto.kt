package com.rezalaki.cryptobycompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rezalaki.cryptobycompose.utils.Constants

@Entity(tableName = Constants.TABLE_CRYPTO)
data class Crypto(
	@field:SerializedName("price_change_percentage_24h")
	val priceChangePercentage24h: Double? = null,

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("total_volume")
	val totalVolume: Double? = null,

	@field:SerializedName("price_change_24h")
	val priceChange24h: Double? = null,

	@field:SerializedName("atl_change_percentage")
	val atlChangePercentage: Double? = null,

	@field:SerializedName("market_cap_rank")
	val marketCapRank: Double? = null,

	@field:SerializedName("atl_date")
	val atlDate: String? = null,

	@field:SerializedName("roi")
	val roi: Roi? = null,

	@field:SerializedName("market_cap_change_24h")
	val marketCapChange24h: Double? = null,

	@field:SerializedName("market_cap")
	val marketCap: Double? = null,

	@field:SerializedName("ath")
	val ath: Double? = null,

	@field:SerializedName("high_24h")
	var high24h: Double? = null,

	@field:SerializedName("atl")
	val atl: Double? = null,

	@field:SerializedName("market_cap_change_percentage_24h")
	val marketCapChangePercentage24h: Double? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String = "",

	@field:SerializedName("ath_change_percentage")
	val athChangePercentage: Double? = null,

	@field:SerializedName("ath_date")
	val athDate: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("circulating_supply")
	val circulatingSupply: Double? = null,

	@field:SerializedName("last_updated")
	val lastUpdated: String? = null,

	@field:SerializedName("total_supply")
	val totalSupply: Double? = null,

	@field:SerializedName("fully_diluted_valuation")
	val fullyDilutedValuation: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("low_24h")
	var low24h: Double? = null,

	@field:SerializedName("max_supply")
	val maxSupply: Double? = null,

	@field:SerializedName("current_price")
	var currentPrice: Double? = null
)


data class Roi(

	@field:SerializedName("times")
	val times: Double? = null,

	@field:SerializedName("percentage")
	val percentage: Double? = null,

	@field:SerializedName("currency")
	val currency: String? = null
)

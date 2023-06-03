package com.rezalaki.cryptobycompose.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rezalaki.cryptobycompose.models.Roi

class DatabaseConverters {

    @TypeConverter
    fun jsonToRoi(roiString: String?): Roi? {
        return if (roiString.isNullOrEmpty())
            null
        else
            Gson().fromJson(roiString, Roi::class.java)
    }

    @TypeConverter
    fun roiToJson(roi: Roi?): String {
        return if (roi == null)
            ""
        else
            Gson().toJson(roi)
    }

}
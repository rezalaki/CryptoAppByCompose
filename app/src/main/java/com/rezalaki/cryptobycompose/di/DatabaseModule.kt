package com.rezalaki.cryptobycompose.di

import android.content.Context
import androidx.room.Room
import com.rezalaki.cryptobycompose.db.MainDao
import com.rezalaki.cryptobycompose.db.MainDatabase
import com.rezalaki.cryptobycompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MainDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: MainDatabase) = db.mainDao()

}
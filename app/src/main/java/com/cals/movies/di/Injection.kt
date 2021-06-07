package com.cals.movies.di

import android.content.Context
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.LocalDataSource
import com.cals.movies.data.source.local.room.CatalogueDatabase
import com.cals.movies.data.source.remote.RemoteDataSource
import com.cals.movies.utils.AppExecutors
import com.cals.movies.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): CatalogueRepository {

        val database = CatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        val appExecutors = AppExecutors()

        return CatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}
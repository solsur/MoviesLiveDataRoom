@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package com.cals.movies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cals.movies.DataStatic
import com.cals.movies.data.source.local.LocalDataSource
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.data.source.remote.RemoteDataSource
import com.cals.movies.utils.AppExecutors
import com.cals.movies.utils.PagedListUtil
import com.cals.movies.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val localDataSource = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val catalogueRepository = FakeCatalogueRepository(remote, localDataSource, appExecutors)

    private val movieResponses = DataStatic.generateRemoteDataMovie()
    private val movieDataList = DataStatic.generateStaticMovie()
    private val tvShowDataList = DataStatic.generateStaticTvShow()
    private val movieData = DataStatic.generateStaticMovie()[0]
    private val tvShowData = DataStatic.generateStaticTvShow()[0]
    private val tvShowResponses = DataStatic.generateRemoteDataTvShow()



    @Test
    fun getAllMovies() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getAllMovie()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticTvShow()))
        verify(localDataSource).getAllMovie()
        Assert.assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }


    @Test
    fun getAllTvShow() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getAllTvShow()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticTvShow()))
        verify(localDataSource).getAllTvShow()
        Assert.assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())

    }

    @Test
    fun getMoviesDetail() {
        val dataStaticEntity = MutableLiveData<MovieEntity>()
        dataStaticEntity.value = movieData
        `when`(localDataSource.getDetailMovieById(movieData.movie_id)).thenReturn(dataStaticEntity)
        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticMovie()))
        Assert.assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowDetail() {
        val dataStaticEntity = MutableLiveData<TvShowEntity>()
        dataStaticEntity.value = tvShowData
        `when`(localDataSource.getDetailTvShowById(tvShowData.tvshow_id)).thenReturn(dataStaticEntity)
        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticTvShow()))
        Assert.assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getAllMovieFavorite() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteMovie()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteMovie()
        val movieEntity =
            Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticMovie()))
        Mockito.verify(localDataSource).getFavoriteMovie()
        Assert.assertNotNull(movieEntity.data)
        assertEquals(movieDataList.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getAllTvShowFavorite() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteTvShow()
        val tvShowEntity =
            Resource.success(PagedListUtil.mockPagedList(DataStatic.generateStaticTvShow()))
        Mockito.verify(localDataSource).getFavoriteTvShow()
        Assert.assertNotNull(tvShowEntity.data)
        assertEquals(tvShowDataList.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

}
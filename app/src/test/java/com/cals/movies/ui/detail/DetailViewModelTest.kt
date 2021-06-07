@file:Suppress("DEPRECATION")

package com.cals.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cals.movies.DataStatic
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.ui.detail.movie.DetailViewModel
import com.cals.movies.vo.Resource
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dataStaticMovie = DataStatic.generateStaticMovie()[0]
    private val movie_id = dataStaticMovie.movie_id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var detailMovieObserver: Observer<Resource<MovieEntity>>


    @Before
    fun setUp() {
        viewModel = DetailViewModel(catalogueRepository)
        viewModel.setSelectedMovie(movie_id)
    }


    @Test
    fun getMovie() {

        val movieData = MutableLiveData<MovieEntity>()
        movieData.value = dataStaticMovie

        `when`(catalogueRepository.getDetailMovieById(movie_id)).thenReturn(movieData)
        val tvShowEntity = viewModel.getDetailMovieForTest(movie_id).value as MovieEntity
        Assert.assertNotNull(tvShowEntity)
        assertEquals(dataStaticMovie.movie_id, tvShowEntity.movie_id)
        assertEquals(dataStaticMovie.title, tvShowEntity.title)
        assertEquals(dataStaticMovie.description, tvShowEntity.description)
        assertEquals(dataStaticMovie.img_poster, tvShowEntity.img_poster)
        assertEquals(dataStaticMovie.release_date, tvShowEntity.release_date)

        viewModel.getDetail.observeForever(detailMovieObserver)


    }
}
@file:Suppress("DEPRECATION")

package com.cals.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cals.movies.DataStatic
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.ui.detail.tvshow.DetailTvShowViewModel
import com.cals.movies.vo.Resource
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dataStaticTvShow = DataStatic.generateStaticTvShow()[0]
    private val tvShowId = dataStaticTvShow.tvshow_id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository


    @Mock
    private lateinit var detailTvShowObserver: Observer<Resource<TvShowEntity>>


    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(catalogueRepository)
        viewModel.setSelectedTvShow(tvShowId)
    }

    @Test
    fun getTvShow() {

        val tvShowData = MutableLiveData<TvShowEntity>()
        tvShowData.value = dataStaticTvShow

        Mockito.`when`(catalogueRepository.getDetailTvShowById(tvShowId)).thenReturn(tvShowData)
        val tvShowEntity = viewModel.getTvShowForTest(tvShowId).value as TvShowEntity
        Assert.assertNotNull(tvShowEntity)
        assertEquals(dataStaticTvShow.tvshow_id, tvShowEntity.tvshow_id)
        assertEquals(dataStaticTvShow.title, tvShowEntity.title)
        assertEquals(dataStaticTvShow.description, tvShowEntity.description)
        assertEquals(dataStaticTvShow.img_poster, tvShowEntity.img_poster)
        assertEquals(dataStaticTvShow.release_date, tvShowEntity.release_date)

        viewModel.getDetailTvShow.observeForever(detailTvShowObserver)


    }

}
package com.cals.movies.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.vo.Resource

class TvShowViewModel (private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = catalogueRepository.getAllTvShow()
}

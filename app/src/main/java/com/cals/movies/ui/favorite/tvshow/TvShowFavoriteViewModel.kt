package com.cals.movies.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.TvShowEntity

class TvShowFavoriteViewModel (private val catalogueRepository: CatalogueRepository) : ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>{
        return catalogueRepository.getFavoriteTvShow()
    }

}
package com.cals.movies.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel (private val catalogueRepository: CatalogueRepository) : ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>{
        return catalogueRepository.getFavoriteMovie()
    }

}
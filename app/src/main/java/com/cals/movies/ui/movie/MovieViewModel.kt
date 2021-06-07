package com.cals.movies.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.vo.Resource

class MovieViewModel (private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = catalogueRepository.getAllMovies()
}

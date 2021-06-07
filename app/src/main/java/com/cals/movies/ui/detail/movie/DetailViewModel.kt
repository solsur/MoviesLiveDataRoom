package com.cals.movies.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.vo.Resource

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    val movieId = MutableLiveData<String>()

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    fun getDetailMovieForTest(movieId: String): LiveData<MovieEntity> =
        catalogueRepository.getDetailMovieById(movieId)


    var getDetail: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) {
        catalogueRepository.getMoviesDetail(it)
    }

    fun setFavoriteMovie() {
        val movieResource = getDetail.value
        if (movieResource != null) {
            val detailMovie = movieResource.data

            if (detailMovie != null) {
                val newState = !detailMovie.favorite
                catalogueRepository.setMovieFavorite(detailMovie, newState)
            }
        }
    }

}


package com.cals.movies.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.data.source.local.room.CatalogueDao

class LocalDataSource private constructor(private val catalogueDao: CatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao).apply {
                INSTANCE = this
            }
    }

    fun setFavoriteMovie(movie: MovieEntity) {
        catalogueDao.insertMovies(movie)
    }

    fun setFavoriteTvShow(tvShow: TvShowEntity) {
        catalogueDao.insertTvShows(tvShow)
    }



    fun setMovieFavorite(movie: MovieEntity, NewState: Boolean) {
        movie.favorite = NewState
        catalogueDao.updateMovie(movie)
    }
    fun setTvShowFavorite(tvShow: TvShowEntity, NewState: Boolean) {
        tvShow.favorite = NewState
        catalogueDao.updateTvShow(tvShow)
    }

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = catalogueDao.getAllMovie()

    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = catalogueDao.getAllTvShow()

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = catalogueDao.getFavoriteMovie()

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> = catalogueDao.getFavoriteTvShow()

    fun getDetailMovieById(movieId: String): LiveData<MovieEntity> =
        catalogueDao.getDetailMovieById(movieId)

    fun getDetailTvShowById(tvShowId: String): LiveData<TvShowEntity> =
        catalogueDao.getDetailTvShowById(tvShowId)

    fun insertMoviesList(movies: List<MovieEntity>) = catalogueDao.insertMovie(movies)

    fun insertTvShowList(tvShow: List<TvShowEntity>) = catalogueDao.insertTvShow(tvShow)
    

}
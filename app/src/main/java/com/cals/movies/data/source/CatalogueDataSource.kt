package com.cals.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.vo.Resource

interface CatalogueDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTvShow():LiveData<Resource<PagedList<TvShowEntity>>>

    fun getAllMovie(): LiveData<PagedList<MovieEntity>>

    fun getAllTvShows(): LiveData<PagedList<TvShowEntity>>

    fun getMoviesDetail(movie_id: String): LiveData<Resource<MovieEntity>>

    fun getTvShowDetail(tvshow_id: String): LiveData<Resource<TvShowEntity>>

    fun setFavoriteMovie(movie: MovieEntity)

    fun setFavoriteTvShow(tvShow: TvShowEntity)

    fun setMovieFavorite(movie: MovieEntity, NewState: Boolean)

    fun setTvShowFavorite(tvShow: TvShowEntity, NewState: Boolean)

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>>

    fun getDetailTvShowById(tvShowId: String): LiveData<TvShowEntity>

    fun getDetailMovieById(movieId: String): LiveData<MovieEntity>


}

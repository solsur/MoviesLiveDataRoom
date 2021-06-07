package com.cals.movies.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.local.entity.TvShowEntity

@Dao
interface CatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShow: TvShowEntity): Long


    @Query("SELECT * FROM movie_entities")
    fun getAllMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvShow_entities")
    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM movie_entities where favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshow_entities where favorite = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movieId = :movieId")
    fun getDetailMovieById(movieId: String): LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvshow_entities WHERE tvShowId = :tvShowId")
    fun getDetailTvShowById(tvShowId: String): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Query("DELETE FROM movie_entities WHERE movieId = :id")
    fun deleteMovieById(id: String)

    @Query("DELETE FROM tvshow_entities WHERE tvShowId = :id")
    fun deleteTvShowById(id: String)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)


}
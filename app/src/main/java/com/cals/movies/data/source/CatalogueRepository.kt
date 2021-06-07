package com.cals.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cals.movies.data.source.local.LocalDataSource
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.data.source.remote.ApiResponse
import com.cals.movies.data.source.remote.RemoteDataSource
import com.cals.movies.data.source.remote.response.MovieResponse
import com.cals.movies.data.source.remote.response.TvShowResponse
import com.cals.movies.utils.AppExecutors
import com.cals.movies.vo.Resource

class CatalogueRepository private constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
                                              private val appExecutors: AppExecutors
): CatalogueDataSource {

    companion object {
        @Volatile
        private var instance: CatalogueRepository? = null
        fun getInstance(remoteData: RemoteDataSource, localDataSource: LocalDataSource,
                        appExecutors: AppExecutors): CatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogueRepository(remoteData, localDataSource,
                    appExecutors).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.release_date,
                        response.img_poster)
                    movieList.add(movie)
                }
                localDataSource.insertMoviesList(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }


            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAllTvShow()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.release_date,
                        response.img_poster)
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShowList(tvShowList)
            }
        }.asLiveData()
    }

    override fun getAllMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
    }

    override fun getAllTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
    }


    override fun getMoviesDetail(movie_id: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<MovieResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailMovieById(movie_id)


            override fun shouldFetch(data: MovieEntity?): Boolean =
                data?.movie_id == null || data.movie_id.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.release_date,
                        response.img_poster)
                    movieList.add(movie)
                }
            }
        }.asLiveData()
    }


    override fun getTvShowDetail(tvshow_id: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, List<TvShowResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getDetailTvShowById(tvshow_id)


            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data?.tvshow_id == null || data.tvshow_id.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAllTvShow()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.release_date,
                        response.img_poster)
                    tvShowList.add(tvShow)
                }
            }
        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: MovieEntity) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity){
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow)
        }
    }



    override fun setMovieFavorite(movie: MovieEntity, NewState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, NewState)}


    override fun setTvShowFavorite(tvShow: TvShowEntity, NewState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShow, NewState)}


    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun getDetailTvShowById(tvShowId: String): LiveData<TvShowEntity> =
        localDataSource.getDetailTvShowById(tvShowId)

    override fun getDetailMovieById(movieId: String): LiveData<MovieEntity> =
        localDataSource.getDetailMovieById(movieId)
}
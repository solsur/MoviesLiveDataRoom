package com.cals.movies.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cals.movies.data.source.CatalogueRepository
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.vo.Resource

class DetailTvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    val tvShowId = MutableLiveData<String>()


    fun setSelectedTvShow(tvShow_id: String) {
        this.tvShowId.value = tvShow_id
    }

    fun getTvShowForTest(movieId: String): LiveData<TvShowEntity> =
        catalogueRepository.getDetailTvShowById(movieId)

    var getDetailTvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvShowId) {
        catalogueRepository.getTvShowDetail(it)
    }

    fun setFavoriteTvShow() {
        val tvShowResource = getDetailTvShow.value
        if (tvShowResource != null) {
            val detailTvShow = tvShowResource.data

            if (detailTvShow != null) {
                val newState = !detailTvShow.favorite
                catalogueRepository.setTvShowFavorite(detailTvShow, newState)
            }
        }
    }

}


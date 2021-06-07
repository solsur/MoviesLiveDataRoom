package com.cals.movies.ui.detail.tvshow

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cals.movies.R
import com.cals.movies.data.source.local.entity.TvShowEntity
import com.cals.movies.databinding.ActivityDetailTvShowBinding
import com.cals.movies.databinding.ContentDetailBinding
import com.cals.movies.viewmodel.ViewModelFactory
import com.cals.movies.vo.Status
import kotlinx.android.synthetic.main.activity_detail.progress_bar
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : AppCompatActivity() {
    private lateinit var contentDetailBinding: ContentDetailBinding
    private lateinit var activityDetailTvShowBinding: ActivityDetailTvShowBinding

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailTvShowBinding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        contentDetailBinding = activityDetailTvShowBinding.detailContentTvShow

        setContentView(activityDetailTvShowBinding.root)

        setSupportActionBar(activityDetailTvShowBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun detailTvShow(tvShowEntity: TvShowEntity) {
        contentDetailBinding.tvItemTitle.text = tvShowEntity.title
        contentDetailBinding.tvItemRelease.text = tvShowEntity.release_date
        contentDetailBinding.tvItemOverview.text = tvShowEntity.description


        Glide.with(this)
            .load(tvShowEntity.img_poster)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loader)
                    .error(R.drawable.ic_error))
            .into(contentDetailBinding.imgDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail_tvshow, menu)
        this.menu = menu
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]
        val extras = intent.extras
        val tvshowId = extras?.getString(EXTRA_TVSHOW)
        if (tvshowId != null) {

            viewModel.setSelectedTvShow(tvshowId)
            viewModel.getDetailTvShow.observe(this, {

                if (it != null) {
                    when (it.status) {
                        Status.LOADING -> {
                            progress_bar.visibility = View.VISIBLE
                            detail_content_tvShow.visibility = View.INVISIBLE
                        }
                        Status.SUCCESS -> if (it.data != null) {
                            progress_bar.visibility = View.GONE
                            detail_content_tvShow.visibility = View.VISIBLE
                            val state = it.data.favorite
                            detailTvShow(it.data)
                            setBookmarkState(state)

                        }
                        Status.ERROR -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Terjadi kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.active_favorite) {
            val factory = ViewModelFactory.getInstance(this)
            val viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]
                viewModel.setFavoriteTvShow()
                return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.active_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border)
        }
    }

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }
}
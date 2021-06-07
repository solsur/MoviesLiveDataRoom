package com.cals.movies.ui.detail.movie

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
import com.cals.movies.data.source.local.entity.MovieEntity
import com.cals.movies.databinding.ActivityDetailBinding
import com.cals.movies.databinding.ContentDetailBinding
import com.cals.movies.viewmodel.ViewModelFactory
import com.cals.movies.vo.Status
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var contentDetailBinding: ContentDetailBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)


        contentDetailBinding = activityDetailBinding.detailContentMovie

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun detailMovie(movieEntity: MovieEntity) {
        contentDetailBinding.tvItemTitle.text = movieEntity.title
        contentDetailBinding.tvItemRelease.text = movieEntity.release_date
        contentDetailBinding.tvItemOverview.text = movieEntity.description


        Glide.with(this)
            .load(movieEntity.img_poster)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loader)
                .error(R.drawable.ic_error))
            .into(contentDetailBinding.imgDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val extras = intent.extras
        val movieId = extras?.getString(EXTRA_MOVIE)

        if (movieId != null) {

            viewModel.setSelectedMovie(movieId)
            viewModel.getDetail.observe(this, {

                if (it != null) {
                    when (it.status) {
                        Status.LOADING -> {
                            progress_bar.visibility = View.VISIBLE
                            detail_content_movie.visibility = View.INVISIBLE
                        }
                        Status.SUCCESS -> if (it.data != null) {
                            progress_bar.visibility = View.GONE
                            detail_content_movie.visibility = View.VISIBLE
                            val state = it.data.favorite
                            setFavoriteState(state)
                            detailMovie(it.data)
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
            val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
            viewModel.setFavoriteMovie()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.active_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border)
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}
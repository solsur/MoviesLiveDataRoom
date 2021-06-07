package com.cals.movies.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cals.movies.databinding.FragmentMovieFavoriteBinding
import com.cals.movies.ui.movie.MovieAdapter
import com.cals.movies.viewmodel.ViewModelFactory

class MovieFavoriteFragment : Fragment() {
    private lateinit var fragmentMovieFavoriteBinding: FragmentMovieFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieFavoriteBinding =
            FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentMovieFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(requireActivity())
            )[MovieFavoriteViewModel::class.java]

        val movieAdapter = MovieAdapter()

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
            movieAdapter.notifyDataSetChanged()
        })

        with(fragmentMovieFavoriteBinding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}
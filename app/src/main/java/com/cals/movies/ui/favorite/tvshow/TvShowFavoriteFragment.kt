package com.cals.movies.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cals.movies.databinding.FragmentTvShowFavoriteBinding
import com.cals.movies.ui.tvshow.TvShowAdapter
import com.cals.movies.viewmodel.ViewModelFactory

class TvShowFavoriteFragment : Fragment() {
    private lateinit var fragmentTvShowFavoriteBinding: FragmentTvShowFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowFavoriteBinding =
            FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(requireActivity())
            )[TvShowFavoriteViewModel::class.java]

        val tvShowAdapter = TvShowAdapter()

        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, {
            tvShowAdapter.submitList(it)
            tvShowAdapter.notifyDataSetChanged()
        })

        with(fragmentTvShowFavoriteBinding.rvTvShow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }
}
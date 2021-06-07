package com.cals.movies.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cals.movies.PagerAdapter
import com.cals.movies.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = PagerAdapter(view.context, childFragmentManager)
        fragmentFavoriteBinding.viewPager.adapter = sectionsPagerAdapter
        fragmentFavoriteBinding.tabLayout.setupWithViewPager(fragmentFavoriteBinding.viewPager)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

}
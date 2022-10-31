package com.vanapic.food.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vanapic.food.R
import com.vanapic.food.core.ui.SectionsPagerFavoriteAdapter
import com.vanapic.food.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_favorite_food,
            R.string.title_favorite_beverage
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            val sectionsPagerAdapter = SectionsPagerFavoriteAdapter(activity as AppCompatActivity)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            (activity as AppCompatActivity).getSupportActionBar()?.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
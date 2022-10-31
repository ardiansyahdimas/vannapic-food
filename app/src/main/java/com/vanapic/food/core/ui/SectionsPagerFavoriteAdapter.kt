package com.vanapic.food.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vanapic.food.ui.favorite.FavoriteBeverageFragment
import com.vanapic.food.ui.favorite.FavoriteFoodFragment

class SectionsPagerFavoriteAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteFoodFragment()
            1 -> fragment = FavoriteBeverageFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
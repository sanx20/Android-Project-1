package com.group18.androidproject1.ui.screens.root


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.group18.androidproject1.R
import com.group18.androidproject1.ui.screens.anime.AnimeFragment
import com.group18.androidproject1.ui.screens.favourites.FavouritesFragment
import com.group18.androidproject1.ui.screens.reviews.ReviewsFragment

class RootFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_root, container, false)

        val bottomNav: BottomNavigationView = view.findViewById(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            replaceFragment(AnimeFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.anime_bottom_nav -> {
                    replaceFragment(AnimeFragment())
                    true
                }
                R.id.reviews_bottom_nav -> {
                    replaceFragment(ReviewsFragment())
                    true
                }
                R.id.favourites_bottom_nav -> {
                    replaceFragment(FavouritesFragment())
                    true
                }
                else -> false
            }
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.home_frame_layout, fragment)
            .commit()
    }
}
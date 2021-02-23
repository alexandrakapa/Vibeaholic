package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragments.Search

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Music Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Music Fragment")
                val musicFragment = Search()
                musicFragment.arguments = bundle
                return musicFragment
            }
            1 -> {
                // # Movies Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Movies Fragment")
                val moviesFragment = Search()
                moviesFragment.arguments = bundle
                return moviesFragment
            }
            2 -> {
                // # Books Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Books Fragment")
                val booksFragment = Search()
                booksFragment.arguments = bundle
                return booksFragment
            }
            else -> return Search()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
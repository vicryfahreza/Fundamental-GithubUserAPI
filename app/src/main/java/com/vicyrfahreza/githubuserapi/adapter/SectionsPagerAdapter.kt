package com.vicyrfahreza.githubuserapi.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vicyrfahreza.githubuserapi.fragment.FollowerFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_POSITION, position)
            putString(FollowerFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}
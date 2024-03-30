package com.example.githubusers.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusers.fragment.Follower
import com.example.githubusers.fragment.Following

class SectionsPagerAdapter(activity: AppCompatActivity, private val nama: String?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Follower.newInstance(nama ?: "")
            1 -> Following.newInstance(nama ?: "")
            else -> throw IllegalArgumentException("Invalid Pos")
        }
    }
}
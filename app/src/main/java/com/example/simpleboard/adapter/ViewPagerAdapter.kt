package com.example.simpleboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.simpleboard.ui.fragment.AddFragment
import com.example.simpleboard.ui.fragment.ChatFragment
import com.example.simpleboard.ui.fragment.HomeFragment
import com.example.simpleboard.ui.fragment.ProfileFragment
import com.example.simpleboard.ui.fragment.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(HomeFragment(), SearchFragment(), AddFragment(), ProfileFragment(), ChatFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
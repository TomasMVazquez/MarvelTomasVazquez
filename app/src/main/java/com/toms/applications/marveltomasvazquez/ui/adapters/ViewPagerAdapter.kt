package com.toms.applications.marveltomasvazquez.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * To Inflate the OnBoarding screens
 * used on OnBoarding Fragment
 */
class ViewPagerAdapter(private val list: ArrayList<Fragment>,fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}
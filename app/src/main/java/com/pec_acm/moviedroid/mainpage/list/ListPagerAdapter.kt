package com.pec_acm.moviedroid.mainpage.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ListPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> WatchingListFragment()
            2 -> CompletedListFragment()
            3 -> OnHoldListFragment()
            4 -> DroppedListFragment()
            5 -> PlanToWatchListFragment()
            else -> AllListFragment()
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}
package com.pec_acm.moviedroid.mainpage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pec_acm.moviedroid.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val listPager = binding.listPager
        listPager.adapter = ListPagerAdapter(this)
        TabLayoutMediator(binding.listTabLayout, listPager) { tab: TabLayout.Tab, position: Int ->
            val tabText: String = when (position) {
                1 -> "Watching"
                2 -> "Completed"
                3 -> "On Hold"
                4 -> "Dropped"
                5 -> "Plan to Watch"
                else -> "All"
            }
            tab.text = tabText
        }.attach()
        return view
    }
}
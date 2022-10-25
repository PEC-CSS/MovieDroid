package com.pec_acm.moviedroid.mainpage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

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
                1 -> getString(R.string.watching_tab)
                2 -> getString(R.string.completed_tab)
                3 -> getString(R.string.on_hold_tab)
                4 -> getString(R.string.dropped_tab)
                5 -> getString(R.string.plan_to_watch_tab)
                else -> getString(R.string.all_tab)
            }
            tab.text = tabText
        }.attach()
        return view
    }
}
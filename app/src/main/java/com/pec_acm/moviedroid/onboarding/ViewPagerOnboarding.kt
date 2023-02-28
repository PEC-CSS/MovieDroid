package com.pec_acm.moviedroid.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pec_acm.moviedroid.databinding.FragmentViewPagerOnboardingBinding
import com.pec_acm.moviedroid.onboarding.screens.*


class ViewPagerOnboarding : Fragment() {
    lateinit var binding: FragmentViewPagerOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        Log.v("asdf", "on create veiw inside")
        binding = FragmentViewPagerOnboardingBinding.inflate(inflater, container, false)
        val fragmentList = arrayListOf(
            OnboardingScreenOne(),
            OnboardingScreenTwo(),
            OnboardingScreenThree()
        )
        val adapter = ViewPagerAdapter(
                fragmentList,
                childFragmentManager,
                lifecycle
        )
        Log.v("asdf", "adapter here")
        binding.viewPagerr.adapter = adapter

        return binding.root
    }
}
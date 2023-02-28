package com.pec_acm.moviedroid.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentOnboardingScreenThreeBinding

class OnboardingScreenThree : Fragment() {
    lateinit var binding: FragmentOnboardingScreenThreeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentOnboardingScreenThreeBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerOnboarding_to_signInFragment)
        }
        return binding.root
    }
}
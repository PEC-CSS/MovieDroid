package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pec_acm.moviedroid.databinding.FragmentPersonDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonDetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    lateinit var binding: FragmentPersonDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        return binding.root
    }
}
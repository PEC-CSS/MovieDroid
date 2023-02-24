package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.FragmentPersonDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PersonDetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args: PersonDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentPersonDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getPersonDetail(args.itemID)

        detailViewModel.personDetailList.observe(viewLifecycleOwner){ personDetail ->
            binding.collapsingToolbarLayout.title = personDetail.name

            if (personDetail.profile_path != null)
            {
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w780" + personDetail.profile_path)
                        .into(binding.image)
            }
            binding.overview.text = personDetail.biography
        }
        // TODO: expand collapse button
        // TODO: known for recycler view
        return binding.root
    }
}
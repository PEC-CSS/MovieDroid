package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.FragmentTvDetailBinding

class TvDetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private val args: TvDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentTvDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvDetailBinding.inflate(inflater, container,false)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getTVShowDetail(args.itemID)
        detailViewModel.tvDetailList.observe(viewLifecycleOwner){tvDetail->
            if (tvDetail.backdrop_path!=null){
                Glide.with(this).load("https://image.tmdb.org/t/p/w780"+ tvDetail.backdrop_path)
                    .into(binding.image)
            }
            binding.hello.text = tvDetail.name
            var genres = ""
            for (i in tvDetail.genres){
                genres+=i.name + "  "
            }
            binding.genre.text = genres
            binding.overview.text = tvDetail.overview
        }
        return binding.root
    }
}
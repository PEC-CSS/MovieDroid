package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    lateinit var binding : FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.hello.text = args.itemID.toString()
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getMovieDetail(args.itemID)
        detailViewModel.movieDetailList.observe(viewLifecycleOwner){movieDetail ->
            Glide.with(this).load(movieDetail.poster_path).into(binding.image)
            binding.hello.text = movieDetail.title
            binding.genre.text = movieDetail.genres[0].name
            binding.overview.text = movieDetail.overview
        }
        return binding.root
    }

}
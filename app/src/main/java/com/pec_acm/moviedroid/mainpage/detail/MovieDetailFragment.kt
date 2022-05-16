package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private val args: MovieDetailFragmentArgs by navArgs()

    lateinit var binding : FragmentMovieDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getMovieDetail(args.itemID)
        detailViewModel.movieDetailList.observe(viewLifecycleOwner){movieDetail ->
            if (movieDetail.backdrop_path!=null) {
                Glide.with(this).load("https://image.tmdb.org/t/p/w780" + movieDetail.backdrop_path)
                    .into(binding.image)
            }
            binding.hello.text = movieDetail.title
            var genres = ""
            for (i in movieDetail.genres){
                genres+=i.name + "  "
            }
            binding.genre.text = genres

            binding.overview.text = movieDetail.overview
        }
        return binding.root
    }

}
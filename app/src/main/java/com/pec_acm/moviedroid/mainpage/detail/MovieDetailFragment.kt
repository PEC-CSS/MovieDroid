package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentMovieDetailBinding
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.adapters.VideoAdapter
import com.pec_acm.moviedroid.mainpage.adapters.CreditsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args: MovieDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentMovieDetailBinding

    var expandedText: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getMovieDetail(args.itemID)
        detailViewModel.getMovieVideo(args.itemID)
        detailViewModel.movieDetailList.observe(viewLifecycleOwner){movieDetail ->
            binding.collapsingToolbarLayout.title = movieDetail.title

            if (movieDetail.backdrop_path != null) {
                Glide.with(this).load("https://image.tmdb.org/t/p/w780" + movieDetail.backdrop_path)
                    .into(binding.image)
            }
            var genres = ""
            for (i in movieDetail.genres) {
                genres += i.name + "  "
            }
            binding.genre.text = genres

            binding.overview.text = movieDetail.overview
            detailViewModel.setFavItem(FirebaseAuth.getInstance().uid!!, movieDetail.toListItem())
            detailViewModel.isFav.observe(viewLifecycleOwner) { fav ->
                if (fav) {
                    binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24)
                }
                binding.favBtn.setOnClickListener {
                    if (fav) {
                        detailViewModel.removeFavItem(FirebaseAuth.getInstance().uid!!, movieDetail.toListItem())
                        binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24)
                    }
                    else {
                        detailViewModel.addFavItem(FirebaseAuth.getInstance().uid!!, movieDetail.toListItem())
                        binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24)
                    }
                }
            }
        }
        binding.expandCollapse.setOnClickListener {
            expandedText = !expandedText
            if(expandedText){
                binding.overview.maxLines = Int.MAX_VALUE
                binding.expandCollapse.rotation = 180f
            } else {
                binding.overview.maxLines = 4
                binding.expandCollapse.rotation = 0f
            }
        }

        detailViewModel.movieVideoDetails.observe(viewLifecycleOwner){ movieTvVideo ->
            binding.videoRcv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = VideoAdapter(requireContext(),movieTvVideo.results)
            }
        }


        //movie credits
        detailViewModel.getMovieCredits(args.itemID)
        detailViewModel.movieCreditsList.observe(viewLifecycleOwner) { movieCredits ->
            binding.rvMovieCredits.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMovieCredits.adapter = CreditsAdapter(requireContext(), movieCredits.crew, getString(R.string.movie_item_category))
        }


        return binding.root
    }
    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(androidx.core.R.menu.example_menu, menu)
    }*/
}
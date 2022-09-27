package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
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
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getTVShowDetail(args.itemID)
        detailViewModel.tvDetailList.observe(viewLifecycleOwner){tvDetail->
            binding.collapsingToolbarLayout.title = tvDetail.name
                if (tvDetail.backdrop_path!=null){
                Glide.with(this).load("https://image.tmdb.org/t/p/w780"+ tvDetail.backdrop_path)
                    .into(binding.image)
            }
            var genres = ""
            for (i in tvDetail.genres){
                genres+=i.name + "  "
            }
            binding.genre.text = genres
            binding.overview.text = tvDetail.overview
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(androidx.core.R.menu.example_menu, menu)
    }
}
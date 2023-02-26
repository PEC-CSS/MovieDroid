package com.pec_acm.moviedroid.mainpage.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentTvDetailBinding
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.adapters.CastAdapter
import com.pec_acm.moviedroid.mainpage.adapters.VideoAdapter
import com.pec_acm.moviedroid.mainpage.adapters.CrewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private val args: TvDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentTvDetailBinding

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getTVShowDetail(args.itemID)
        detailViewModel.getTvVideo(args.itemID)
        detailViewModel.tvDetailList.observe(viewLifecycleOwner){tvDetail->
            binding.collapsingToolbarLayout.title = tvDetail.name
            if (tvDetail.backdrop_path != null) {
                Glide.with(this).load("https://image.tmdb.org/t/p/w780" + tvDetail.backdrop_path)
                    .into(binding.image)
            }
            var genres = ""
            for (i in tvDetail.genres) {
                genres += i.name + "  "
            }
            binding.genre.text = genres
            binding.overview.text = tvDetail.overview
            detailViewModel.setFavItem(FirebaseAuth.getInstance().uid!!, tvDetail.toListItem())
            detailViewModel.isFav.observe(viewLifecycleOwner) { fav ->
                if (fav) {
                    binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24)
                }
                binding.favBtn.setOnClickListener {
                    if (fav) {
                        detailViewModel.removeFavItem(FirebaseAuth.getInstance().uid!!, tvDetail.toListItem())
                        binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24)
                    }
                    else {
                        detailViewModel.addFavItem(FirebaseAuth.getInstance().uid!!, tvDetail.toListItem())
                        binding.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24)
                    }
                }
            }
        }


        detailViewModel.tvVideoDetails.observe(viewLifecycleOwner){ tvVideo ->
            binding.videoRcv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = VideoAdapter(requireContext(),tvVideo.results)
            }
        }


        //tv credits
        detailViewModel.getTvCredits(args.itemID)
        detailViewModel.tvCreditsList.observe(viewLifecycleOwner) { tvCredits ->
            binding.rvTvCast.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTvCast.adapter = CastAdapter(requireContext(), tvCredits.cast, getString(R.string.tv_item_category))

            binding.rvTvCrew.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTvCrew.adapter = CrewAdapter(requireContext(), tvCredits.crew, getString(R.string.tv_item_category))
        }

        return binding.root
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(androidx.core.R.menu.example_menu, menu)
    }*/
}
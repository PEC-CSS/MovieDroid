package com.pec_acm.moviedroid.mainpage.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.pec_acm.moviedroid.databinding.FragmentAllItemsBinding
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.adapters.NormalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllItems : Fragment() {
    private var _binding : FragmentAllItemsBinding? = null
    val binding get() = _binding!!
    private val args: AllItemsArgs by navArgs()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllItemsBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.allItemsRV.layoutManager = GridLayoutManager(context, 2)
        when (args.type) {
            "topMovies" -> {
                viewModel.getTopMovies()
                val topMovies = mutableListOf<ListItem>()
                viewModel.topMovies.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        topMovies.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(topMovies,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
            "topTVSeries" -> {
                viewModel.getTopTVShows()
                val topTVSeries = mutableListOf<ListItem>()
                viewModel.topTVShows.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        topTVSeries.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(topTVSeries,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
            "popularMovies" -> {
                viewModel.getPopularMovies()
                val popularMovies = mutableListOf<ListItem>()
                viewModel.popularMovies.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        popularMovies.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(popularMovies,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
            "popularTVSeries" -> {
                viewModel.getPopularTVShows()
                val popularTVSeries = mutableListOf<ListItem>()
                viewModel.popularTVShows.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        popularTVSeries.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(popularTVSeries,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
            "comingSoon" -> {
                viewModel.getUpcomingMovies()
                val comingSoon = mutableListOf<ListItem>()
                viewModel.upcomingMovies.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        comingSoon.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(comingSoon,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
            "inTheaters" -> {
                viewModel.getNowPlayingMovies()
                val nowplaying = mutableListOf<ListItem>()
                viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
                    for (element in it) {
                        val listItem = element.toListItem()
                        nowplaying.add(listItem)
                    }
                    val topMoviesAdapter = NormalAdapter(nowplaying,requireContext())
                    binding.allItemsRV.adapter = topMoviesAdapter
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
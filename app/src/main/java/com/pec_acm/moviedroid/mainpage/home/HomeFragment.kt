package com.pec_acm.moviedroid.mainpage.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentHomeBinding
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.adapters.HorizontalAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding : FragmentHomeBinding? = null
    private val binding get()  = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.rv250movies.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        binding.rvTop250tvshows.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        viewModel.getTopMovies()
        val topMovies = mutableListOf<ListItem>()
        viewModel.topMovies.observe(viewLifecycleOwner) {

            for (i in 0..8) {
                val listItem = it[i].toListItem()
                topMovies.add(listItem)
            }
            val topMoviesAdapter = HorizontalAdapter(requireContext())
            topMoviesAdapter.setItemList(topMovies)
            binding.rv250movies.adapter = topMoviesAdapter
        }
        viewModel.getTopTVShows()
        val topTVShows = mutableListOf<ListItem>()
        viewModel.topTVShows.observe(viewLifecycleOwner){

            for (i in 0..8){
                val listItem = it[i].toListItem()
                topTVShows.add(listItem)
            }
            val topTVShowsAdapter = HorizontalAdapter(requireContext())
            topTVShowsAdapter.setItemList(topTVShows)
            binding.rvTop250tvshows.adapter = topTVShowsAdapter
        }
        viewModel.getPopularMovies()
        val popularMovies = mutableListOf<ListItem>()
        viewModel.popularMovies.observe(viewLifecycleOwner){

            for (i in 0..8) {
                val listItem = it[i].toListItem()
                popularMovies.add(listItem)
            }
            val popularMoviesAdapter = HorizontalAdapter( requireContext())
            popularMoviesAdapter.setItemList(popularMovies)
            binding.rvMostPopularMovies.adapter = popularMoviesAdapter
        }

        viewModel.getPopularTVShows()
        val popularTVShows = mutableListOf<ListItem>()
        viewModel.popularTVShows.observe(viewLifecycleOwner){

            for (i in 0..8){
                val listItem = it[i].toListItem()
                popularTVShows.add(listItem)
            }
            val mostPopularTVShows = HorizontalAdapter(requireContext())
            mostPopularTVShows.setItemList(popularTVShows)
            binding.rvMostPopularTVShows.adapter = mostPopularTVShows
        }
        viewModel.getNowPlayingMovies()
        val nowPlayingMovies = mutableListOf<ListItem>()
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner){

            for (i in 0..8){
                val listItem = it[i].toListItem()
                nowPlayingMovies.add(listItem)
            }
            val nowPlayingMoviesAdapter = HorizontalAdapter(requireContext())
            nowPlayingMoviesAdapter.setItemList(nowPlayingMovies)
            binding.rvInTheaters.adapter = nowPlayingMoviesAdapter
        }

        viewModel.getUpcomingMovies()
        val upcomingMovies = mutableListOf<ListItem>()
        viewModel.upcomingMovies.observe(viewLifecycleOwner){

            for (i in 0..8){
                val listItem = it[i].toListItem()
                upcomingMovies.add(listItem)
            }
            val upcomingMoviesAdapter = HorizontalAdapter(requireContext())
            upcomingMoviesAdapter.setItemList(upcomingMovies)
            binding.rvComingSoon.adapter = upcomingMoviesAdapter
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
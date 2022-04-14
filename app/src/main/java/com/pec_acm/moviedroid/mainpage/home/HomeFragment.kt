package com.pec_acm.moviedroid.mainpage.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentHomeBinding
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
        viewModel.topMovies.observe(viewLifecycleOwner) {
            val movieList = arrayListOf<Pair<String,String>>()
            for (i in 0..8) {
                movieList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].title))
            }
            val topMoviesAdapter = HorizontalAdapter(movieList, requireContext())
            binding.rv250movies.adapter = topMoviesAdapter
        }
        viewModel.getTopTVShows()
        viewModel.topTVShows.observe(viewLifecycleOwner){
            val tvShowList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                tvShowList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].name))
            }
            Log.d("TV","$tvShowList")
            val topTVShowsAdapter = HorizontalAdapter(tvShowList,requireContext())
            binding.rvTop250tvshows.adapter = topTVShowsAdapter
        }
        viewModel.getPopularMovies()
        viewModel.popularMovies.observe(viewLifecycleOwner){
            val movieList = arrayListOf<Pair<String,String>>()
            for (i in 0..8) {
                movieList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].title))
            }
            val popularMoviesAdapter = HorizontalAdapter(movieList, requireContext())
            binding.rvMostPopularMovies.adapter = popularMoviesAdapter
        }

        viewModel.getPopularTVShows()
        viewModel.popularTVShows.observe(viewLifecycleOwner){
            val tvShowList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                tvShowList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].name))
            }
            val mostPopularTVShows = HorizontalAdapter(tvShowList,requireContext())
            binding.rvMostPopularTVShows.adapter = mostPopularTVShows
        }

        viewModel.getNowPlayingMovies()
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner){
            val theaterItemList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                theaterItemList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].title))
            }
            val nowPlayingMoviesAdapter = HorizontalAdapter(theaterItemList,requireContext())
            binding.rvInTheaters.adapter = nowPlayingMoviesAdapter
        }

        viewModel.getUpcomingMovies()
        viewModel.upcomingMovies.observe(viewLifecycleOwner){
            val comingSoonItemList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                comingSoonItemList.add(Pair("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+it[i].poster_path,it[i].title))
            }
            val upcomingMoviesAdapter = HorizontalAdapter(comingSoonItemList,requireContext())
            binding.rvComingSoon.adapter = upcomingMoviesAdapter
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
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
        // Inflate the layout for this fragment
        viewModel.get250Movies()
        viewModel.top250Movies.observe(viewLifecycleOwner) {
            val result = it
            val movies = result.items
            val movieList = arrayListOf<Pair<String,String>>()
            for (i in 0..8) {
                movieList.add(Pair(movies[i].image,movies[i].fullTitle))
            }
            val top250MovieAdapter = HorizontalAdapter(movieList, requireContext())
            binding.rv250movies.adapter = top250MovieAdapter
        }
        viewModel.get250TVShows()
        viewModel.top250TVShows.observe(viewLifecycleOwner){
            val result = it
            val tvShow = result.items
            val tvShowList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                tvShowList.add(Pair(tvShow[i].image,tvShow[i].fullTitle))
            }
            Log.d("TV","$tvShowList")
            val top250TVShowAdapter = HorizontalAdapter(tvShowList,requireContext())
            binding.rvTop250tvshows.adapter = top250TVShowAdapter
        }
        viewModel.getMostPopularMovies()
        viewModel.mostPopularMovies.observe(viewLifecycleOwner){
            val result = it
            val movies = result.items
            val movieList = arrayListOf<Pair<String,String>>()
            for (i in 0..8) {
                movieList.add(Pair(movies[i].image,movies[i].fullTitle))
            }
            val mostPopularMoviesAdapter = HorizontalAdapter(movieList, requireContext())
            binding.rvMostPopularMovies.adapter = mostPopularMoviesAdapter
        }

        viewModel.getMostPopularTVshows()
        viewModel.mostPopularTVShows.observe(viewLifecycleOwner){
            val result = it
            val tvShow = result.items
            val tvShowList = arrayListOf<Pair<String,String>>()
            for (i in 0..8){
                tvShowList.add(Pair(tvShow[i].image,tvShow[i].fullTitle))
            }
            val mostPopularTVShows = HorizontalAdapter(tvShowList,requireContext())
            binding.rvMostPopularTVShows.adapter = mostPopularTVShows
        }

        viewModel.getInTheaters()
        viewModel.inTheaters.observe(viewLifecycleOwner){
            val result = it
            val theaterItem = result.items
            val theaterItemList = arrayListOf<Pair<String,String>>()
            for (i in theaterItem.indices){
                theaterItemList.add(Pair(theaterItem[i].image,theaterItem[i].fullTitle))
            }
            val inTheatersAdapter = HorizontalAdapter(theaterItemList,requireContext())
            binding.rvInTheaters.adapter = inTheatersAdapter
        }

        viewModel.getComingSoon()
        viewModel.comingSoon.observe(viewLifecycleOwner){
            val result = it
            val comingSoonItem = result.items
            val comingSoonItemList = arrayListOf<Pair<String,String>>()
            for (i in comingSoonItem.indices){
                comingSoonItemList.add(Pair(comingSoonItem[i].image,comingSoonItem[i].fullTitle))
            }
            val comingSoonAdapter = HorizontalAdapter(comingSoonItemList,requireContext())
            binding.rvComingSoon.adapter = comingSoonAdapter
        }
        return view
    }

}
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
            val movieList = arrayListOf<String>()
            for (i in 0..8) {
                movieList.add(movies[i].image)
            }
            val top250MovieAdapter = HorizontalAdapter(movieList, requireContext())
            binding.rv250movies.adapter = top250MovieAdapter
        }
        viewModel.get250TVShows()
        viewModel.top250TVShows.observe(viewLifecycleOwner){
            val result = it
            val tvShow = result.items
            val tvShowList = arrayListOf<String>()
            for (i in 0..8){
                tvShowList.add(tvShow[i].image)
            }
            Log.d("TV","$tvShowList")
            val top250TVShowAdapter = HorizontalAdapter(tvShowList,requireContext())
            binding.rvTop250tvshows.adapter = top250TVShowAdapter
        }
        return view
    }

}
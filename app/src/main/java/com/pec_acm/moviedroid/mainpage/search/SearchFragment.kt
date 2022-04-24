package com.pec_acm.moviedroid.mainpage.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.list.ListAdapter

class SearchFragment : Fragment() {
    private lateinit var searchListAdapter : ListAdapter
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchList = view.findViewById<RecyclerView>(R.id.search_list)
        val searchText = view.findViewById<SearchView>(R.id.search_text)
        searchListAdapter = ListAdapter(requireContext())
        searchList.adapter = searchListAdapter
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.movieSearchList.observe(viewLifecycleOwner){
            val searchItems = mutableListOf<ListItem>()
            for(i in it.indices)
            {
                searchItems.add(it[i].toListItem())
            }
            searchListAdapter.setItemList(searchItems)
        }
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query==null) return true
                searchViewModel.searchMovie(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return view
    }

}
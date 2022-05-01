package com.pec_acm.moviedroid.mainpage.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.list.ListAdapter
import com.pec_acm.moviedroid.mainpage.list.ListViewModel

class SearchFragment : Fragment() {
    private lateinit var searchListAdapter : ListAdapter
    private lateinit var searchViewModel: SearchViewModel
    val searchItems = mutableListOf<ListItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchList = view.findViewById<RecyclerView>(R.id.search_list)
        val searchText = view.findViewById<SearchView>(R.id.search_text)
        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        searchListAdapter = ListAdapter(requireContext(),listViewModel, this)
        searchList.adapter = searchListAdapter
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.getUser(FirebaseAuth.getInstance().uid!!)
        searchViewModel.movieSearchList.observe(viewLifecycleOwner){ resultList ->
            searchViewModel.user.observe(viewLifecycleOwner){ user->

                for(i in resultList.indices)
                {
                    var listItem = resultList[i].toListItem()
                    for(item in user.userList)
                    {
                        if(item.id==listItem.id)
                        {
                            listItem=item
                            break
                        }
                    }
                    searchItems.add(listItem)
                }
                searchListAdapter.setItemList(searchItems)
            }
        }
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query==null) return true
                searchViewModel.searchMovie(query)
                searchText.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if (newText=="") {
                     searchItems.clear()
                     searchViewModel.movieSearchList.value = arrayListOf()
                     searchViewModel.tvShowSearchList.value = arrayListOf()
                     searchListAdapter.notifyDataSetChanged()
                 }
                return true
            }
        })
        return view
    }

}
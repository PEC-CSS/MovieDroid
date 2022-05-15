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
import com.pec_acm.moviedroid.databinding.FragmentSearchBinding
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.list.ListAdapter
import com.pec_acm.moviedroid.mainpage.list.ListViewModel

class SearchFragment : Fragment() {
    private lateinit var searchListAdapter : ListAdapter
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    var searchItems = mutableListOf<ListItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container, false)
        val view = binding.root
        val searchList = view.findViewById<RecyclerView>(R.id.search_list)
        val searchText = view.findViewById<SearchView>(R.id.search_text)
        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        searchListAdapter = ListAdapter(requireContext(),listViewModel, this, false)
        searchList.adapter = searchListAdapter
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.getUser(FirebaseAuth.getInstance().uid!!)
        searchViewModel.movieSearchList.observe(viewLifecycleOwner){ resultList ->
            searchViewModel.user.observe(viewLifecycleOwner){ user->
                searchItems.clear()
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
        searchViewModel.tvShowSearchList.observe(viewLifecycleOwner) { resultList ->
            searchViewModel.user.observe(viewLifecycleOwner) { user ->
                searchItems.clear()
                for (i in resultList.indices) {
                    var listItem = resultList[i].toListItem()
                    for (item in user.userList) {
                        if (item.id == listItem.id) {
                            listItem = item
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
                val checkedChipID = binding.searchChipGroup.checkedChipId
                if (checkedChipID ==binding.movieChip.id) {
                    binding.movieChip.setChipBackgroundColorResource(R.color.search_toggle)
                    searchViewModel.searchMovie(query)
                }
                else if (checkedChipID == binding.tvChip.id){
                    searchViewModel.searchTvShow(query)
                }

                searchText.clearFocus()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 if (newText=="") {
                     searchItems.clear()
                     binding.searchChipGroup.clearCheck()
                     searchViewModel.movieSearchList.value = arrayListOf()
                     searchViewModel.tvShowSearchList.value = arrayListOf()
                     searchListAdapter.notifyDataSetChanged()
                 }

                binding.searchChipGroup.setOnCheckedChangeListener { _, id ->
                    if (id ==binding.movieChip.id && newText!="") {
                        binding.movieChip.setChipBackgroundColorResource(R.color.search_toggle)
                        searchViewModel.searchMovie(newText.toString())
                    }
                    else if (id == binding.tvChip.id && newText!=""){
                        searchViewModel.searchTvShow(newText.toString())
                    }
                }
                return true
            }
        })
        return view
    }

}
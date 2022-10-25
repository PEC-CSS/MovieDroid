package com.pec_acm.moviedroid.mainpage.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentSearchBinding
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.ListItem.Companion.toListItem
import com.pec_acm.moviedroid.mainpage.list.ListAdapter
import com.pec_acm.moviedroid.mainpage.list.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchListAdapter : ListAdapter
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var searchText : SearchView
    var searchItems = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container, false)

        val view = binding.root
        val searchList = binding.searchList
        searchText = binding.searchText

        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        searchListAdapter = ListAdapter(requireContext(),listViewModel, this, false)
        searchList.adapter = searchListAdapter

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.getUser(FirebaseAuth.getInstance().uid!!)

        binding.movieChip.setOnClickListener {
            searchText.queryHint = "Search Movies"
        }

        binding.tvChip.setOnClickListener {
            searchText.queryHint = "Search TV Shows"
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner){ searchResult ->
            when(searchResult)
            {
                SearchResult.FOUND -> {
                    binding.searchList.visibility = View.VISIBLE
                    binding.searchLottie.visibility = View.GONE
                    binding.noSearchText.visibility = View.GONE
                }
                SearchResult.NOT_FOUND -> {
                    binding.searchList.visibility = View.GONE
                    binding.searchLottie.visibility = View.VISIBLE
                    binding.searchLottie.apply {
                        setAnimation(R.raw.no_search)
                        playAnimation()
                    }
                    binding.noSearchText.visibility = View.VISIBLE
                }
                SearchResult.SEARCHING -> {
                    binding.searchList.visibility = View.GONE
                    binding.searchLottie.visibility = View.VISIBLE
                    binding.noSearchText.visibility = View.GONE
                    binding.searchLottie.setAnimation(R.raw.searching)
                    binding.searchLottie.playAnimation()
                }
            }
        }

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
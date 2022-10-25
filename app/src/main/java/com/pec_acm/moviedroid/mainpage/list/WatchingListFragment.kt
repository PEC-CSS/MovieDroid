package com.pec_acm.moviedroid.mainpage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.FragmentWatchingListBinding
import com.pec_acm.moviedroid.firebase.ListItem

class WatchingListFragment : Fragment() {
    private lateinit var binding: FragmentWatchingListBinding
    private lateinit var listViewModel: ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchingListBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val watchingList = binding.watchingList
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        listViewModel.getUser(FirebaseAuth.getInstance().uid!!)
        val listAdapter = ListAdapter(
            requireContext(),
            listViewModel, this
        )
        watchingList.adapter = listAdapter
        listViewModel.user.observe(viewLifecycleOwner,
            Observer { user ->
                if (user == null) return@Observer
                val itemList = ArrayList<ListItem>()
                for (i in user.userList.indices) {
                    val listItem = user.userList[i]
                    if (listItem.status == 1) itemList.add(listItem)
                }
                itemList.sortBy { it.name }
                listAdapter.setItemList(itemList)
            })
        return view
    }
}
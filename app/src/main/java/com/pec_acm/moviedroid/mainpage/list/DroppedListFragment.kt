package com.pec_acm.moviedroid.mainpage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.FragmentDroppedListBinding
import com.pec_acm.moviedroid.firebase.ListItem

class DroppedListFragment : Fragment() {
    private lateinit var binding: FragmentDroppedListBinding
    private lateinit var listViewModel: ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDroppedListBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val droppedList = binding.droppedList
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        listViewModel.getUser(FirebaseAuth.getInstance().uid!!)
        val listAdapter = ListAdapter(
            requireContext(),
            listViewModel, this
        )
        droppedList.adapter = listAdapter
        listViewModel.user.observe(viewLifecycleOwner,
            Observer { user ->
                if (user == null) return@Observer
                val itemList = ArrayList<ListItem>()
                for (i in user.userList.indices) {
                    val listItem = user.userList[i]
                    if (listItem.status == 4) itemList.add(listItem)
                }
                listAdapter.setItemList(itemList)
            })
        return view
    }
}
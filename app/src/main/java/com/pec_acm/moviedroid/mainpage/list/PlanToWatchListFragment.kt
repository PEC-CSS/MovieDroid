package com.pec_acm.moviedroid.mainpage.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.FragmentPlanToWatchListBinding
import com.pec_acm.moviedroid.firebase.ListItem
import java.util.ArrayList

class PlanToWatchListFragment : Fragment() {
    private var binding: FragmentPlanToWatchListBinding? = null
    private var listViewModel: ListViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanToWatchListBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        val planToWatchList = binding!!.planToWatchList
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel!!.getUser(FirebaseAuth.getInstance().uid!!)
        val listAdapter = ListAdapter(requireContext(), listViewModel!!, this)
        planToWatchList.adapter = listAdapter
        listViewModel!!.user.observe(viewLifecycleOwner, Observer { user ->
            if (user == null) return@Observer
            val itemList = ArrayList<ListItem>()
            for (i in user.userList.indices) {
                val listItem = user.userList[i]
                if (listItem.status == 5) itemList.add(listItem)
            }
            itemList.sortBy { it.name }
            listAdapter.setItemList(itemList)
        })
        return view
    }
}
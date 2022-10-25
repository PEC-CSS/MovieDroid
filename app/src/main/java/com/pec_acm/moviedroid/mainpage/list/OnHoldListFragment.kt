package com.pec_acm.moviedroid.mainpage.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.FragmentOnHoldListBinding
import com.pec_acm.moviedroid.firebase.ListItem
import java.util.ArrayList

class OnHoldListFragment : Fragment() {
    private var binding: FragmentOnHoldListBinding? = null
    private var listViewModel: ListViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnHoldListBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        val onHoldList = binding!!.onHoldList
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel!!.getUser(FirebaseAuth.getInstance().uid!!)
        val listAdapter = ListAdapter(requireContext(), listViewModel!!, this)
        onHoldList.adapter = listAdapter
        listViewModel!!.user.observe(viewLifecycleOwner, Observer { user ->
            if (user == null) return@Observer
            val itemList = ArrayList<ListItem>()
            for (i in user.userList.indices) {
                val listItem = user.userList[i]
                if (listItem.status == 3) itemList.add(listItem)
            }
            itemList.sortBy { it.name }
            listAdapter.setItemList(itemList)
        })
        return view
    }
}
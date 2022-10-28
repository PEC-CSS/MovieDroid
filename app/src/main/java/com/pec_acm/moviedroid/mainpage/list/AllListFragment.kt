package com.pec_acm.moviedroid.mainpage.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.FragmentAllListBinding
import com.pec_acm.moviedroid.firebase.ListItem

class AllListFragment : Fragment() {
    private var _binding: FragmentAllListBinding? = null
    private val binding get() = _binding!!
    private var listViewModel: ListViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllListBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val allList = binding.allList
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel!!.getUser(FirebaseAuth.getInstance().uid!!)
        val listAdapter = ListAdapter(requireContext(), listViewModel!!, this)
        allList.adapter = listAdapter
        listViewModel!!.user.observe(viewLifecycleOwner, Observer { user ->
            if (user == null) return@Observer
            if(user.userList.isEmpty()) {
                binding.lottieAnimation.visibility = View.VISIBLE
                binding.noEntriesText.visibility = View.VISIBLE
            }
            else {
                binding.lottieAnimation.visibility = View.GONE
                binding.noEntriesText.visibility = View.GONE
            }
            listAdapter.setItemList(user.userList.sortedByStatus())
        })
        return view
    }

    private fun MutableList<ListItem>.sortedByStatus(): MutableList<ListItem> {
        return this.sortedBy { it.name }.sortedBy { it.status }.toMutableList()
    }

}
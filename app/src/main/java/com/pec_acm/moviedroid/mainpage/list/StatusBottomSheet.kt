package com.pec_acm.moviedroid.mainpage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem

class StatusBottomSheet(val listViewModel: ListViewModel,val listItem: ListItem) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout,container,false)
        val statusList : ListView = view.findViewById(R.id.options_list)
        val statusOptions = arrayOf(
            getString(R.string.watching_tab),
            getString(R.string.completed_tab),
            getString(R.string.on_hold_tab),
            getString(R.string.dropped_tab),
            getString(R.string.plan_to_watch_tab)
        )
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,statusOptions)
        statusList.adapter = arrayAdapter
        statusList.setOnItemClickListener { adapterView, view, i, id ->
            if(i+1!=listItem.status)
            {
                if(listItem.status==0)
                {
                    listItem.status=i+1
                    listViewModel.addItem(FirebaseAuth.getInstance().uid!!,listItem)
                }
                else
                {
                    listViewModel.setItemStatus(FirebaseAuth.getInstance().uid!!,listItem.id,i+1)
                }
            }
            dismiss()
        }
        return view
    }
}
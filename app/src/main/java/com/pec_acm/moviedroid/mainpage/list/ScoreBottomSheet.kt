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

class ScoreBottomSheet(val listViewModel: ListViewModel,val listItem: ListItem) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        val statusList: ListView = view.findViewById(R.id.options_list)
        val statusOptions = arrayOf(
            getString(R.string.rating_10),
            getString(R.string.rating_9),
            getString(R.string.rating_8),
            getString(R.string.rating_7),
            getString(R.string.rating_6),
            getString(R.string.rating_5),
            getString(R.string.rating_4),
            getString(R.string.rating_3),
            getString(R.string.rating_2),
            getString(R.string.rating_1),
            getString(R.string.remove_rating)
        )
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statusOptions)
        statusList.adapter = arrayAdapter
        statusList.setOnItemClickListener { adapterView, view, i, id ->
            if (10 - i != listItem.personalScore) {
                listViewModel.setItemScore(FirebaseAuth.getInstance().uid!!, listItem.id, 10-i)
            }
            dismiss()
        }
        return view
    }
}
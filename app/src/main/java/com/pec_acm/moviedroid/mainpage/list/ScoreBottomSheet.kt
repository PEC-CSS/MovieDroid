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
            "10 - Masterpiece",
            "9 - Amazing",
            "8 - Great",
            "7 - Good",
            "6 - Fine",
            "5 - Average",
            "4 - Bad",
            "3 - Very Bad",
            "2 - Horrible",
            "1 - Nightmare"
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
package com.pec_acm.moviedroid.mainpage.list

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem

class StatusBottomSheet(val listViewModel: ListViewModel, val listItem: ListItem) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        val statusList: ListView = view.findViewById(R.id.options_list)
        val statusOptions = arrayOf(
            getString(R.string.watching_tab),
            getString(R.string.completed_tab),
            getString(R.string.on_hold_tab),
            getString(R.string.dropped_tab),
            getString(R.string.plan_to_watch_tab)
        )
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_checked, statusOptions)
        statusList.adapter = arrayAdapter
        statusList.choiceMode = ListView.CHOICE_MODE_SINGLE

        if (listItem.status in 1..statusOptions.size)
            statusList.setItemChecked(listItem.status - 1, true)

        statusList.setOnItemClickListener { adapterView, view, i, id ->
            if (i + 1 != listItem.status) {
                if (listItem.status == 0) {
                    listItem.status = i + 1
                    listViewModel.addItem(FirebaseAuth.getInstance().uid!!, listItem)
                } else {
                    listViewModel.setItemStatus(
                        FirebaseAuth.getInstance().uid!!,
                        listItem.id,
                        i + 1
                    )
                }
            } else {
                showDialog(requireContext())
            }
            dismiss()
        }
        return view
    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(context)

        dialog.apply {
            setContentView(R.layout.warning_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setDimAmount(0.70F)
            setCancelable(false)

            val title = findViewById<TextView>(R.id.title)
            title.text = "Warning !!"

            val subtitle = findViewById<TextView>(R.id.subtitle)
            subtitle.text = "Do you want to remove the item from your list ?"

            val yesBtn = findViewById<Button>(R.id.yesBtn)
            val noBtn = findViewById<TextView>(R.id.noBtn)
            yesBtn.setOnClickListener {
                listViewModel.removeItem(FirebaseAuth.getInstance().uid!!, listItem)
                dismiss()
            }
            noBtn.setOnClickListener {
                dismiss()
            }
        }.show()
    }

}
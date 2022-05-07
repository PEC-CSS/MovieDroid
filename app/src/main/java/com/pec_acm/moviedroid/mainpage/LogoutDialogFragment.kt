package com.pec_acm.moviedroid.mainpage

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R

class LogoutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.are_you_sure_you_want_to_logout)
            .setPositiveButton(R.string.yes) { _, _ ->
                FirebaseAuth.getInstance().signOut()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                requireActivity().onBackPressed()
            }
            .create()
    }
}

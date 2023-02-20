package com.pec_acm.moviedroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.ActivityProfileBinding
import com.pec_acm.moviedroid.mainpage.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        profileViewModel.setUserRatingValues(user!!.uid)
        //update the info

        if (user != null) {
            binding.textName.text = user.displayName
            binding.textEmail.text = user.email

             Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(binding.imgProfile)

            profileViewModel.overallRating.observe(this) {rate ->
                binding.OverallRateText.text = rate
            }
            profileViewModel.tvRating.observe(this) {rate ->
                binding.TVRateText.text = rate
            }
            profileViewModel.movieRating.observe(this) {rate ->
                binding.MovieRateText.text = rate
            }
        }

        binding.backBtnProfile.setOnClickListener {
            finish()
        }

    }
}
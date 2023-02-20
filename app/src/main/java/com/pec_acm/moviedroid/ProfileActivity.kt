package com.pec_acm.moviedroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pec_acm.moviedroid.databinding.ActivityProfileBinding
import com.pec_acm.moviedroid.firebase.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser

        val databaseReference = Firebase.database.reference
        val userReference = databaseReference.child("Users")

        //update the info

        if (user != null) {
            binding.textName.text = user.displayName
            binding.textEmail.text = user.email

             Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(binding.imgProfile)

            var movieRate = 0
            var tvRate = 0
            var movieCount = 0
            var tvCount = 0

            userReference.child(user.uid).get().addOnCompleteListener {
                val userDbRef = it.result.getValue(User::class.java)
                for (item in userDbRef!!.userList)
                {
                    if (item.personalScore != 0)
                    {
                        if (item.category == "tv")
                        {
                            tvCount += 1
                            tvRate += item.personalScore
                        }
                        else
                        {
                            movieCount += 1
                            movieRate += item.personalScore
                        }
                    }
                }
                binding.OverallRateText.text = if (movieCount + tvCount != 0) buildString {
                    append("Overall Rating:\n")
                    append((movieRate + tvRate) / (movieCount + tvCount))
                    append("/10")
                } else "Rate something to view your average rating!"
                binding.MovieRateText.text = if (movieCount != 0) buildString {
                    append("Movie Rating:\n")
                    append(movieRate / movieCount)
                    append("/10")
                } else ""
                binding.TVRateText.text = if (tvCount != 0) buildString {
                    append("TV Shows Rating:\n")
                    append((tvRate / tvCount))
                    append("/10")
                } else ""
            }
        }

        binding.backBtnProfile.setOnClickListener {
            finish()
        }

    }
}
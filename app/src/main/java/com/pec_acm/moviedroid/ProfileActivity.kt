package com.pec_acm.moviedroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
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

        // https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.setEntryLabelTextSize(15f)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.centerText = "text in middle?"
        binding.pieChart.setCenterTextSize(24f)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.isEnabled = false


        var entries = ArrayList<PieEntry>()
        entries.add(PieEntry(0.2f, "asdf"))
        entries.add(PieEntry(0.15f, "qwer"))
        entries.add(PieEntry(0.10f, "zxcv"))
        entries.add(PieEntry(0.25f, "poiu"))
        entries.add(PieEntry(0.3f, "lkjh"))

        var dataset = PieDataSet(entries, "title maibi")
        var data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        binding.pieChart.data = data
        binding.pieChart.invalidate()


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
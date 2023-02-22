package com.pec_acm.moviedroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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

        // https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(Color.TRANSPARENT)
        binding.pieChart.holeRadius = 35f
        binding.pieChart.transparentCircleRadius = 40f
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.setEntryLabelTextSize(13.5f)
        binding.pieChart.setEntryLabelColor(Color.WHITE)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        binding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        binding.pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        binding.pieChart.legend.setDrawInside(false)
        binding.pieChart.legend.isEnabled = true
        binding.pieChart.legend.textColor = Color.WHITE
        binding.pieChart.legend.textSize = 15f
        // legend.setCustom(List<LegendEntry>)


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(0.2f, resources.getString(R.string.watching_status)))
        entries.add(PieEntry(0.15f, resources.getString(R.string.completed_status)))
        entries.add(PieEntry(0.10f, resources.getString(R.string.on_hold_status)))
        entries.add(PieEntry(0.25f, resources.getString(R.string.dropped_status)))
        entries.add(PieEntry(0.3f, resources.getString(R.string.plan_to_watch_status)))

        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.green))
        colors.add(resources.getColor(R.color.purple_700))
        colors.add(resources.getColor(R.color.yellow))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.grey))

        val dataset = PieDataSet(entries, "")
        dataset.colors = colors
        val data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.pieChart))
        data.setValueTextSize(13.5f)
        data.setValueTextColor(Color.WHITE)

        binding.pieChart.data = data
        binding.pieChart.invalidate()
        binding.pieChart.isRotationEnabled = true

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
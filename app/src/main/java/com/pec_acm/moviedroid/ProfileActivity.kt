package com.pec_acm.moviedroid

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.databinding.ActivityProfileBinding
import com.pec_acm.moviedroid.mainpage.adapters.FavsAdapter
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
        profileViewModel.setListItemsCounts(user!!.uid)
        profileViewModel.setFavsList(user!!.uid)

        //update the info
        if (user != null) {
            binding.textName.text = user.displayName
            binding.textEmail.text = user.email

             Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(binding.imgProfile)

            profileViewModel.userFavItems.observe(this) {favs ->
                if (favs.size == 0)
                {
                    binding.noFavsText.visibility = View.VISIBLE
                }
                else
                {
                    binding.noFavsText.visibility = View.INVISIBLE
                }
                binding.favListRv.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.favListRv.adapter = FavsAdapter(this, favs)
            }

            profileViewModel.listItemsCounts.observe(this) {values ->
                if (values.sum() == 0)
                {
                    binding.pieChart.visibility = View.INVISIBLE
                    binding.divider3.visibility = View.INVISIBLE
                }
                else
                {
                    binding.pieChart.visibility = View.VISIBLE
                    binding.divider3.visibility = View.VISIBLE
                }
                binding.pieChart.isDrawHoleEnabled = true
                binding.pieChart.setHoleColor(Color.TRANSPARENT)
                binding.pieChart.holeRadius = 35f
                binding.pieChart.transparentCircleRadius = 40f

                binding.pieChart.setUsePercentValues(false)
                binding.pieChart.setEntryLabelTextSize(12.5f)
                binding.pieChart.setEntryLabelColor(Color.WHITE)
                binding.pieChart.description.isEnabled = false

                binding.pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                binding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                binding.pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
                binding.pieChart.legend.setDrawInside(false)
                binding.pieChart.legend.textColor = binding.MovieRateText.currentTextColor
                binding.pieChart.legend.textSize = 15f

                val entries = ArrayList<PieEntry>()
                val labelStrings = arrayOf(
                    resources.getString(R.string.watching_status),
                    resources.getString(R.string.completed_status),
                    resources.getString(R.string.on_hold_status),
                    resources.getString(R.string.dropped_status),
                    resources.getString(R.string.plan_to_watch_status)
                )
                val colorList = arrayOf(
                    resources.getColor(R.color.green),
                    resources.getColor(R.color.purple_700),
                    resources.getColor(R.color.yellow),
                    resources.getColor(R.color.red),
                    resources.getColor(R.color.grey)
                )
                val colors = ArrayList<Int>()
                for (idx in 0..4)
                {
                    if (values[idx] != 0)
                    {
                        entries.add(PieEntry(values[idx].toFloat(), labelStrings[idx]))
                        colors.add(colorList[idx])
                    }
                }
                val dataset = PieDataSet(entries, "")
                dataset.colors = colors
                val vf: ValueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "" + value.toInt()
                    }
                }
                val data = PieData(dataset)
                data.setDrawValues(true)
                data.setValueTextSize(15f)
                data.setValueTextColor(Color.WHITE)
                data.setValueFormatter(vf)

                binding.pieChart.data = data
                binding.pieChart.invalidate()
                binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
            }
            profileViewModel.overallRating.observe(this) {rate ->
                binding.OverallRateText.text = rate
            }
            profileViewModel.tvRating.observe(this) {rate ->
                binding.TVRateText.text = rate
            }
            profileViewModel.movieRating.observe(this) { rate ->
                binding.MovieRateText.text = rate
            }
        }

        binding.backBtnProfile.setOnClickListener {
            finish()
        }

    }
}
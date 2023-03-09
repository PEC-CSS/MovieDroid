package com.pec_acm.moviedroid.mainpage.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.MainActivity
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.FragmentProfileBinding
import com.pec_acm.moviedroid.mainpage.adapters.FavsAdapter


class ProfileFragment : Fragment(), OnChartValueSelectedListener {

    private lateinit var profileViewModel: ProfileViewModel

    lateinit var binding: FragmentProfileBinding
    private val entries = ArrayList<PieEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as MainActivity).hideBottomNavigation()
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val user = FirebaseAuth.getInstance().currentUser
        profileViewModel.setUserRatingValues(user!!.uid)
        profileViewModel.setListItemsCounts(user!!.uid)
        profileViewModel.setFavsList(user!!.uid)

        if (user != null) {
            binding.textName.text = user.displayName
            binding.textEmail.text = user.email

            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(binding.imgProfile)

            profileViewModel.userFavItems.observe(viewLifecycleOwner) { favs ->
                if (favs.size == 0) {
                    binding.noFavsText.visibility = View.VISIBLE
                } else {
                    binding.noFavsText.visibility = View.INVISIBLE
                }
                binding.favListRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.favListRv.adapter = FavsAdapter(requireContext(), favs, "ProfileFragment")
            }
            profileViewModel.listItemsCounts.observe(viewLifecycleOwner) {values ->
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
                entries.clear()
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
                binding.pieChart.setOnChartValueSelectedListener(this)
            }
            profileViewModel.overallRating.observe(viewLifecycleOwner) {rate ->
                binding.OverallRateText.text = rate
            }
            profileViewModel.tvRating.observe(viewLifecycleOwner) {rate ->
                binding.TVRateText.text = rate
            }
            profileViewModel.movieRating.observe(viewLifecycleOwner) { rate ->
                binding.MovieRateText.text = rate
            }
        }

        return binding.root
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.v("asdf", entries[h!!.x.toInt()].label)
    }

    override fun onNothingSelected() {

    }
}
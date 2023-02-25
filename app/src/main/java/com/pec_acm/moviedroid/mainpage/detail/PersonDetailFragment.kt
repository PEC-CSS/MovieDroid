package com.pec_acm.moviedroid.mainpage.detail

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.FragmentPersonDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class PersonDetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args: PersonDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentPersonDetailBinding

    var expandedText: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getPersonDetail(args.itemID)

        detailViewModel.personDetailList.observe(viewLifecycleOwner){ personDetail ->
            binding.collapsingToolbarLayout.title = personDetail.name

            if (personDetail.profile_path != null)
            {
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w780" + personDetail.profile_path)
                        .into(binding.image)
            }
            if (personDetail.birthday != null)
            {
                binding.birthDay.visibility = View.VISIBLE
                val date = LocalDate.parse(personDetail.birthday)
                binding.birthDay.text = buildString {
                    append("Born on: ")
                    append(date.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")))
                }
            } else { binding.birthDay.visibility = View.GONE }
            if (personDetail.deathday != null)
            {
                binding.deadDay.visibility = View.VISIBLE
                val date = LocalDate.parse(personDetail.deathday)
                binding.deadDay.text = buildString {
                    append("Died on: ")
                    append(date.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")))
                }
            } else { binding.deadDay.visibility = View.GONE }
            binding.overview.text = personDetail.biography
        }
        // make sure to display everything thats recieved lol
        binding.expandCollapse.setOnClickListener {
            expandedText = !expandedText
            if(expandedText){
                binding.overview.maxLines = Int.MAX_VALUE
                binding.expandCollapse.rotation = 180f
            } else {
                binding.overview.maxLines = 4
                binding.expandCollapse.rotation = 0f
            }
        }
        // TODO: known for recycler view
        return binding.root
    }
}
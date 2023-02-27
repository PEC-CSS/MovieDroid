package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.databinding.PersonListItemBinding
import com.pec_acm.moviedroid.model.PersonResult

class PeopleSearchAdapter(val context: Context, private val personList: MutableList<PersonResult>) :
        Adapter<PeopleSearchAdapter.PeopleViewHolder>() {

    inner class PeopleViewHolder(val binding: PersonListItemBinding) : ViewHolder(binding.root) {
        val name = binding.itemName
        val image = binding.itemImage
        val knownForDep = binding.itemKnownForDepartment
        val knownFor = binding.itemKnownForWork
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeopleViewHolder(PersonListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = personList[position]
        holder.name.text = person.name
        holder.knownForDep.text = person.known_for_department
        if (person.profile_path != null)
        {
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w138_and_h175_face" + person.profile_path)
                    .centerCrop()
                    .into(holder.image)
        }
        var knownForTemp = "Popular Works: "
        for (item in person.known_for)
        {
            if (item.media_type == "tv")
            {
                knownForTemp += item.name + ", "
            }
            else if (item.media_type == "movie")
            {
                knownForTemp += item.title + ", "
            }
        }
        holder.knownFor.text = knownForTemp.dropLast(2)
    }
}
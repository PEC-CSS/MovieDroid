package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pec_acm.moviedroid.databinding.PersonListItemBinding
import com.pec_acm.moviedroid.model.PersonResult

class PeopleSearchAdapter(val context: Context, private val personList: MutableList<PersonResult>) :
        Adapter<PeopleSearchAdapter.PeopleViewHolder>() {

    inner class PeopleViewHolder(val binding: PersonListItemBinding) : ViewHolder(binding.root) {
        val name = binding.itemTitle
        val image = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeopleViewHolder(PersonListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.name.text = personList[position].name
    }
}
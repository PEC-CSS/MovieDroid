package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem

class FavsAdapter(val context: Context, private val favList: MutableList<ListItem>) :
    Adapter<FavsAdapter.FavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cast_crew_item, parent, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.fNAme.text = favList[position].name
        val profile_full_path = favList[position].posterUrl
        Glide.with(context)
                .load(profile_full_path)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.fImage)
    }

    class FavViewHolder(itemView: View) : ViewHolder(itemView) {
        val fNAme = itemView.findViewById<TextView>(R.id.tvCreditName)
        val fImage = itemView.findViewById<ImageView>(R.id.imgCredit)
    }
}
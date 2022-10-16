package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.model.Crew

class CreditsAdapter(val context: Context, val crewList: List<Crew>) : Adapter<CreditsAdapter.CreditsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cast_crew_item,parent,false)
        return CreditsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        holder.cName.text = crewList[position].name
        Glide.with(context)
            .load(crewList[position].profile_path).into(holder.cImage)
    }

    override fun getItemCount(): Int {
        return crewList.size
    }

    class CreditsViewHolder(itemView: View) : ViewHolder(itemView) {
        val cName = itemView.findViewById<TextView>(R.id.tvCreditName)
        val cImage = itemView.findViewById<ImageView>(R.id.imgCredit)
    }
}
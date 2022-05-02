package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.mainpage.home.HomeFragmentDirections

class HorizontalAdapter(private val images:ArrayList<Pair<Int,Pair<String,String>>> , val context: Context):RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    inner class ViewHolder(itemView : View, var itemID:Int?=null):RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imginwa)
        val movieTitle:TextView = itemView.findViewById(R.id.movie_title)
        init {
            itemView.setOnClickListener {
                itemID?.let {
                    val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                    itemView.findNavController().navigate(direction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.image_rv,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val image = images[position]
        Glide.with(context).load(image.second.first).into(holder.image)
        holder.movieTitle.text = image.second.second
        holder.itemID = image.first
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
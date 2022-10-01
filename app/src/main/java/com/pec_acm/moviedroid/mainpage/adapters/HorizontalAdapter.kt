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
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.mainpage.home.HomeFragmentDirections

class HorizontalAdapter( val context: Context):RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    private var itemList: MutableList<ListItem> = mutableListOf()

    inner class ViewHolder(itemView : View, var itemID:Int?=null, var itemCategory: String? =null):RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imginwa)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    if (itemCategory == context.getString(R.string.movie_item_category)) {
                        val direction =
                            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it)
                        itemView.findNavController().navigate(direction)
                    } else if (itemCategory == context.getString(R.string.tv_item_category)) {
                        itemView.findNavController()
                            .navigate(HomeFragmentDirections.actionHomeFragmentToTvDetailFragment(it))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.image_rv,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = itemList[position]
        Glide.with(context).load(listItem.posterUrl).into(holder.image)
        holder.movieTitle.text = listItem.name
        holder.itemID = listItem.id
        holder.itemCategory = listItem.category
    }

    fun setItemList(newList: MutableList<ListItem>) {
        itemList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 8
    }
}
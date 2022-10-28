package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.App
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.mainpage.home.AllItemsDirections


class NormalAdapter(private val images: List<ListItem>, val context: Context) :
    RecyclerView.Adapter<NormalAdapter.ViewHolder>() {
    inner class ViewHolder(
        itemView: View,
        var itemCategory: String? = null,
        var itemID: Int? = null
    ) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imginwa)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    val app = (context.applicationContext as App)
                    if(app.isOnline) {
                        if (itemCategory == context.getString(R.string.movie_item_category)) {
                            val direction =
                                AllItemsDirections.actionAllItemsToMovieDetailFragment(it)
                            itemView.findNavController().navigate(direction)
                        } else if (itemCategory == context.getString(R.string.tv_item_category)) {
                            itemView.findNavController()
                                .navigate(AllItemsDirections.actionAllItemsToTvDetailFragment(it))
                        }
                    }else{
                        Toast.makeText(context,context.getString(R.string.internet_not_available),
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_rv2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        Glide.with(context).load(image.posterUrl).into(holder.image)
        holder.movieTitle.text = image.name
        holder.itemID = image.id
        holder.itemCategory = image.category
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
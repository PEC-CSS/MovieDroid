package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.App
import com.pec_acm.moviedroid.ProfileActivity
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.mainpage.detail.PersonDetailFragmentDirections
import dagger.hilt.android.internal.managers.ViewComponentManager.FragmentContextWrapper

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
        holder.itemID = favList[position].id
        holder.itemType = favList[position].category
        val profile_full_path = favList[position].posterUrl
        Glide.with(context)
                .load(profile_full_path)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.fImage)
    }

    inner class FavViewHolder(itemView: View, var itemID: Int? = null, var itemType: String? = null) : ViewHolder(itemView) {
        val fNAme = itemView.findViewById<TextView>(R.id.tvCreditName)
        val fImage = itemView.findViewById<ImageView>(R.id.imgCredit)

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    val app = (context.applicationContext as App)
                    if (app.isOnline) {
                        if (context is ProfileActivity)
                        {
                            // TODO: make fav list in profile activity clickable
                        }
                        if (context is FragmentContextWrapper)
                        {
                            if (itemType == "movie")
                            {
                                itemView.findNavController().navigate(
                                    PersonDetailFragmentDirections.actionPersonDetailFragmentToMovieDetailFragment(it)
                                )
                            } else if (itemType == "tv") {
                                itemView.findNavController().navigate(
                                    PersonDetailFragmentDirections.actionPersonDetailFragmentToTvDetailFragment(it)
                                )
                            }
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
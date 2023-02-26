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
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.App
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.mainpage.detail.MovieDetailFragmentDirections
import com.pec_acm.moviedroid.mainpage.detail.TvDetailFragmentDirections
import com.pec_acm.moviedroid.model.Cast

class CastAdapter(val context: Context, private val castList: List<Cast>, val fragmentCategory: String) :
        Adapter<CastAdapter.CreditsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cast_crew_item, parent, false)
        return CreditsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        holder.cName.text = castList[position].name
        holder.itemID = castList[position].id
        val profile_full_path =
                "https://image.tmdb.org/t/p/w138_and_h175_face" + castList[position].profile_path
        Glide.with(context)
                .load(profile_full_path)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.cImage)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    inner class CreditsViewHolder(itemView: View, var itemID: Int? = null) : RecyclerView.ViewHolder(itemView) {
        val cName = itemView.findViewById<TextView>(R.id.tvCreditName)
        val cImage = itemView.findViewById<ImageView>(R.id.imgCredit)

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    val app = (context.applicationContext as App)
                    if (app.isOnline) {
                        if (fragmentCategory == context.getString(R.string.movie_item_category))
                        {
                            itemView.findNavController().navigate(
                                    MovieDetailFragmentDirections.actionMovieDetailFragmentToPersonDetailFragment(it)
                            )
                        }
                        else if (fragmentCategory == context.getString(R.string.tv_item_category))
                        {
                            itemView.findNavController().navigate(
                                    TvDetailFragmentDirections.actionTvDetailFragmentToPersonDetailFragment(it)
                            )
                        }
                    }
                    else
                    {
                        Toast.makeText(context,context.getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
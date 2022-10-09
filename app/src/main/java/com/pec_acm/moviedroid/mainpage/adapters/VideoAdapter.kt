package com.pec_acm.moviedroid.mainpage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.mainpage.YoutubeView
import com.pec_acm.moviedroid.model.MovieTvVideoResults

class VideoAdapter(val mContext: Context, val list: List<MovieTvVideoResults>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.single_video_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {
        holder.bind(holder, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(holder: ViewHolder, movieTvVideoResults: MovieTvVideoResults) {
            holder.itemView.findViewById<YoutubeView>(R.id.video)
                .loadData(movieTvVideoResults.key, "text/html", "utf-8")
            holder.itemView.findViewById<TextView>(R.id.name).text = movieTvVideoResults.name
        }
    }

}
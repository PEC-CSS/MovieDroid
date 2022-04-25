package com.pec_acm.moviedroid.mainpage.list

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem

class ListAdapter(val context : Context,val listViewModel: ListViewModel): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var itemList : MutableList<ListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val listItem = itemList[position]
        holder.itemTitle.text = listItem.name
        holder.itemCategory.text = listItem.category.uppercase()
        Glide.with(context).load(listItem.posterUrl).into(holder.itemImage)
        holder.itemScore.text = listItem.score.toString()
        val status: String
        val statusColor: Int
        when(listItem.status)
        {
            1 -> {
                status="watching"
                statusColor=R.color.green
            }
            2 -> {
                status="completed"
                statusColor=R.color.purple_700
            }
            3 -> {
                status="on hold"
                statusColor=R.color.yellow
            }
            4 -> {
                status="dropped"
                statusColor=R.color.red
            }
            5 -> {
                status="plan to watch"
                statusColor=R.color.grey
            }
            else -> {
                status="add to list"
                statusColor=R.color.black
            }
        }
        if(listItem.status>0)
        {
            holder.itemPersonalScore.visibility = View.VISIBLE
            if(listItem.personalScore>0)
            {
                holder.itemPersonalScore.text = listItem.personalScore.toString() + "/10"
            }
            else
            {
                holder.itemPersonalScore.text = "RATE"
            }

        }
        else holder.itemPersonalScore.visibility = View.GONE

        holder.itemPersonalScore.setOnClickListener {
            val bottomSheet = ScoreBottomSheet(listViewModel, listItem)
            bottomSheet.show((context as FragmentActivity).supportFragmentManager,bottomSheet.tag)
        }

        holder.itemStatus.text = status.uppercase()
        holder.itemStatus.setBackgroundResource(statusColor)
        holder.itemStatus.setOnClickListener {
            val bottomSheet = StatusBottomSheet(listViewModel, listItem)
            bottomSheet.show((context as FragmentActivity).supportFragmentManager,bottomSheet.tag)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(newList : MutableList<ListItem>)
    {
        itemList = newList
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemTitle : TextView = itemView.findViewById(R.id.item_title)
        val itemImage : ImageView = itemView.findViewById(R.id.item_image)
        val itemCategory: TextView = itemView.findViewById(R.id.item_category)
        val itemScore : TextView = itemView.findViewById(R.id.item_score)
        val itemStatus : TextView = itemView.findViewById(R.id.item_status)
        val itemPersonalScore : TextView = itemView.findViewById(R.id.item_personal_score)
    }
}
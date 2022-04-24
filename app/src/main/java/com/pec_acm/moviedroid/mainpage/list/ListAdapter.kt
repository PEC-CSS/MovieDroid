package com.pec_acm.moviedroid.mainpage.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.firebase.ListItem

class ListAdapter(val context : Context): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var itemList : MutableList<ListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val listItem = itemList[position]
        holder.itemTitle.setText(listItem.name)
        holder.itemCategory.setText(listItem.category.uppercase())
        Glide.with(context).load(listItem.posterUrl).into(holder.itemImage)
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
        val itemTitle = itemView.findViewById<TextView>(R.id.item_title)
        val itemImage = itemView.findViewById<ImageView>(R.id.item_image)
        val itemCategory = itemView.findViewById<TextView>(R.id.item_category)
    }
}
package com.pec_acm.moviedroid.mainpage.list


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.databinding.MovieListCountItemBinding
import com.pec_acm.moviedroid.databinding.MovieListItemBinding
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.mainpage.search.SearchFragment
import com.pec_acm.moviedroid.mainpage.search.SearchFragmentDirections

class ListAdapter @JvmOverloads constructor(val context : Context, val listViewModel: ListViewModel, val fragment: Fragment?, val showCount: Boolean = true): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val NORMAL_VIEW_HOLDER  = 0
        private const val ITEM_COUNT_VIEW_HOLDER = 1
    }

    private var itemList: MutableList<ListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == NORMAL_VIEW_HOLDER) {
            ListViewHolder(MovieListItemBinding.inflate(layoutInflater, parent, false))
        } else {
            ListViewCountViewHolder(MovieListCountItemBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_COUNT_VIEW_HOLDER) {
            val holder: ListAdapter.ListViewCountViewHolder = viewHolder as ListViewCountViewHolder

            holder.binding.itemListCount.text = context.getString(R.string.num_entries, itemList.size)
            return
        }

        val holder =  viewHolder as ListAdapter.ListViewHolder;

        val listItem = if (showCount) itemList[position - 1] else itemList[position]
        holder.binding.itemTitle.text = listItem.name
        holder.binding.itemCategory.text = listItem.category.uppercase()
        Glide.with(context).load(listItem.posterUrl).into(holder.binding.itemImage)
        holder.binding.itemScore.text = listItem.score.toString()
        holder.itemID = listItem.id
        val status: String
        val statusColor: Int
        when (listItem.status) {
            1 -> {
                status = "watching"
                statusColor = R.color.green
            }
            2 -> {
                status = "completed"
                statusColor = R.color.purple_700
            }
            3 -> {
                status = "on hold"
                statusColor = R.color.yellow
            }
            4 -> {
                status = "dropped"
                statusColor = R.color.red
            }
            5 -> {
                status = "plan to watch"
                statusColor = R.color.grey
            }
            else -> {
                status = "add to list"
                statusColor = R.color.black
            }
        }
        if (listItem.status > 0) {
            holder.binding.itemPersonalScore.visibility = View.VISIBLE
            if (listItem.personalScore > 0) {
                holder.binding.itemPersonalScore.text = listItem.personalScore.toString() + "/10"
            } else {
                holder.binding.itemPersonalScore.text = "RATE"
            }

        } else holder.binding.itemPersonalScore.visibility = View.GONE

        holder.binding.itemPersonalScore.setOnClickListener {
            val bottomSheet = ScoreBottomSheet(listViewModel, listItem)
            bottomSheet.show((context as FragmentActivity).supportFragmentManager, bottomSheet.tag)
        }

        holder.binding.itemStatus.text = status.uppercase()
        holder.binding.itemStatus.setBackgroundResource(statusColor)
        holder.binding.itemStatus.setOnClickListener {
            val bottomSheet = StatusBottomSheet(listViewModel, listItem)
            bottomSheet.show((context as FragmentActivity).supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun getItemCount(): Int {
        return if (showCount && itemList.isNotEmpty()) itemList.size + 1 else itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (showCount && position == 0) ITEM_COUNT_VIEW_HOLDER else NORMAL_VIEW_HOLDER
    }

    fun setItemList(newList: MutableList<ListItem>) {
        itemList = newList
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: MovieListItemBinding, var itemID: Int? = null) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    val searchDirection =
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
                    val listDirection =
                        ListFragmentDirections.actionListFragmentToDetailFragment(it)

                    if (fragment is SearchFragment)
                        itemView.findNavController().navigate(searchDirection)
                    else {
                        itemView.findNavController().navigate(listDirection)
                    }
                }
            }
        }
    }

    inner class ListViewCountViewHolder(val binding: MovieListCountItemBinding): RecyclerView.ViewHolder(binding.root)
}

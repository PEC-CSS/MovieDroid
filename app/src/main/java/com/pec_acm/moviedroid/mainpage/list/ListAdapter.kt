package com.pec_acm.moviedroid.mainpage.list


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.internal.managers.ViewComponentManager


class ListAdapter @JvmOverloads constructor(private val context : Context, private val listViewModel: ListViewModel, val fragment: Fragment?, private val showCount: Boolean = true): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        val holder =  viewHolder as ListAdapter.ListViewHolder

        // Decrement the position by 1 before accessing in case of showCount being true as list size is incremented by 1 to accommodate ListViewCountViewHolder.
        val listItem = if (showCount) itemList[position - 1] else itemList[position]
        holder.binding.itemTitle.text = listItem.name
        holder.binding.itemCategory.text = listItem.category.uppercase()

        Glide.with(context)
            .load(listItem.posterUrl)
            .into(holder.binding.itemImage)

        holder.binding.itemScore.text = listItem.score.toString()
        holder.itemID = listItem.id
        holder.itemCategory = listItem.category

        val status: String
        val statusColor: Int
        when (listItem.status) {
            1 -> {
                status = context.getString(R.string.watching_status)
                statusColor = R.color.green
            }
            2 -> {
                status = context.getString(R.string.completed_status)
                statusColor = R.color.purple_700
            }
            3 -> {
                status = context.getString(R.string.on_hold_status)
                statusColor = R.color.yellow
            }
            4 -> {
                status = context.getString(R.string.dropped_status)
                statusColor = R.color.red
            }
            5 -> {
                status = context.getString(R.string.plan_to_watch_status)
                statusColor = R.color.grey
            }
            else -> {
                status = context.getString(R.string.add_to_list_status)
                statusColor = R.color.black
            }
        }
        if (listItem.status > 0) {
            holder.binding.itemPersonalScore.visibility = View.VISIBLE
            if (listItem.personalScore > 0) {
                holder.binding.itemPersonalScore.text = "${listItem.personalScore}/10"
            } else {
                holder.binding.itemPersonalScore.text = "RATE"
            }

        } else holder.binding.itemPersonalScore.visibility = View.GONE

        val mContext = if(context is ViewComponentManager.FragmentContextWrapper) {
            context.baseContext
        } else {
            context
        }

        holder.binding.itemPersonalScore.setOnClickListener {
            val bottomSheet = ScoreBottomSheet(listViewModel, listItem)
            bottomSheet.show((mContext as FragmentActivity).supportFragmentManager, bottomSheet.tag)
        }

        holder.binding.itemStatus.text = status.uppercase()
        holder.binding.itemStatus.setBackgroundResource(statusColor)
        holder.binding.itemStatus.setOnClickListener {
            val bottomSheet = StatusBottomSheet(listViewModel, listItem)
            bottomSheet.show((mContext as FragmentActivity).supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun getItemCount(): Int {
        // Increment list size by 1 in case of showCount being true to accommodate the ListViewCountViewHolder.
        return if (showCount) itemList.size + 1 else itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (showCount && position == 0) ITEM_COUNT_VIEW_HOLDER else NORMAL_VIEW_HOLDER
    }

    fun setItemList(newList: MutableList<ListItem>) {
        itemList = newList
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: MovieListItemBinding, var itemID: Int? = null, var itemCategory: String? = null) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemID?.let {
                    if (itemCategory == context.getString(R.string.movie_item_category)) {
                        if (fragment is SearchFragment){
                            itemView.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(it))
                        }
                        else{
                           itemView.findNavController().navigate(ListFragmentDirections.actionListFragmentToMovieDetailFragment(it))
                        }
                    } else if (itemCategory == context.getString(R.string.tv_item_category)) {
                        if (fragment is SearchFragment){
                            itemView.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTvDetailFragment(it))
                        }
                        else{
                            itemView.findNavController().navigate(ListFragmentDirections.actionListFragmentToTvDetailFragment(it))
                        }
                    }
                }
            }
        }
    }
    inner class ListViewCountViewHolder(val binding: MovieListCountItemBinding): RecyclerView.ViewHolder(binding.root)
}

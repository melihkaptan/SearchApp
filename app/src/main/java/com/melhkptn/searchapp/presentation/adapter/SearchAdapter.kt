package com.melhkptn.searchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.melhkptn.searchapp.R
import com.melhkptn.searchapp.domain.model.response.Results
import com.melhkptn.searchapp.util.downloadImage
import kotlinx.android.synthetic.main.search_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SearchAdapter :
    PagedListAdapter<Results, SearchAdapter.AdapterViewHolder>(SearchDiffCallback()) {

    class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_item, parent, false)
        return AdapterViewHolder(view)
    }


    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {

        getItem(position)?.trackName?.let { trackName ->
            holder.itemView.textViewName.text = trackName
            holder.itemView.textViewPrice.text = getItem(position)?.trackPrice.toString() + "$"
        } ?: kotlin.run {
            holder.itemView.textViewName.text = getItem(position)?.collectionName
            holder.itemView.textViewPrice.text = getItem(position)?.collectionPrice.toString() + "$"
        }

        val date = LocalDate.parse(getItem(position)?.releaseDate, DateTimeFormatter.ISO_DATE_TIME)
        holder.itemView.textViewReleaseDate.text = date.year.toString()

        getItem(position)?.artworkUrl100?.let {
            holder.itemView.imageView.downloadImage(
                it,
                holder.itemView.context
            )
        }

    }

}

class SearchDiffCallback : DiffUtil.ItemCallback<Results>() {

    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem.collectionId == newItem.collectionId

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem == newItem

}
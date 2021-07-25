package com.lukakrodzaia.moviesdatabase.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.databinding.RvPopularShowsItemBinding
import com.lukakrodzaia.moviesdatabase.databinding.RvSearchItemBinding
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SearchListModel
import com.lukakrodzaia.moviesdatabase.utils.setImage

class SearchAdapter(private val context: Context,
                    private val onShowClick: (id: Int) -> Unit): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var list: List<SearchListModel> = ArrayList()

    fun setItems(shows: List<SearchListModel>) {
        list = shows
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.ViewHolder {
        return ViewHolder(
            RvSearchItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val show = list[position]

        holder.bind(show)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val view: RvSearchItemBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(show: SearchListModel) {
            view.itemName.text = show.name
            view.itemPoster.setImage(show.poster)
            view.itemScore.text = show.score.toString()
            view.itemDate.text = show.date

            view.root.setOnClickListener {
                onShowClick(show.id)
            }
        }
    }
}
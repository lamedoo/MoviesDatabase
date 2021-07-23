package com.lukakrodzaia.moviesdatabase.ui.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.databinding.RvPopularShowsItemBinding
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel

class PopularShowsAdapter(private val context: Context,
                          private val onShowClick: (id: Int) -> Unit): RecyclerView.Adapter<PopularShowsAdapter.ViewHolder>() {
    private var list: List<PopularListModel> = ArrayList()

    fun setItems(shows: List<PopularListModel>) {
        list = shows
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularShowsAdapter.ViewHolder {
        return ViewHolder(
            RvPopularShowsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularShowsAdapter.ViewHolder, position: Int) {
        val show = list[position]

        holder.bind(show)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val view: RvPopularShowsItemBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(show: PopularListModel) {
            view.showTitle.text = show.name
        }
    }
}
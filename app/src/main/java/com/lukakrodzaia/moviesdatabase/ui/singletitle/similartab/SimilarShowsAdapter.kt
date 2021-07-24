package com.lukakrodzaia.moviesdatabase.ui.singletitle.similartab

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.databinding.RvPopularShowsItemBinding
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SimilarListModel
import com.lukakrodzaia.moviesdatabase.utils.setImage

class SimilarShowsAdapter(private val context: Context,
                          private val onShowClick: (id: Int) -> Unit): RecyclerView.Adapter<SimilarShowsAdapter.ViewHolder>() {
    private var list: List<SimilarListModel> = ArrayList()

    fun setItems(shows: List<SimilarListModel>) {
        list = shows
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarShowsAdapter.ViewHolder {
        return ViewHolder(
            RvPopularShowsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SimilarShowsAdapter.ViewHolder, position: Int) {
        val show = list[position]

        holder.bind(show)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val view: RvPopularShowsItemBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(show: SimilarListModel) {
            view.itemName.text = show.name
            view.itemPoster.setImage(show.poster)

            view.root.setOnClickListener {
                onShowClick(show.id)
            }
        }
    }
}
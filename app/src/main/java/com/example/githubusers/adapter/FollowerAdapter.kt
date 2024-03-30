package com.example.githubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.databinding.ItemRowBinding
import com.example.githubusers.remote.FollResponse
import com.squareup.picasso.Picasso

class FollowerAdapter : ListAdapter<FollResponse, FollowerAdapter.ViewHolder>(DIFF_UTIL) {

    class ViewHolder(private val bind: ItemRowBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(foll: FollResponse){
            bind.tvItemName.text = foll.login
            Picasso.get().load(foll.avatarUrl).into(bind.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foll = getItem(position)
        holder.binding(foll)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<FollResponse>(){
            override fun areItemsTheSame(
                oldItem: FollResponse,
                newItem: FollResponse
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: FollResponse,
                newItem: FollResponse
            ): Boolean = oldItem == newItem

        }
    }
}
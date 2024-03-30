package com.example.githubusers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.databinding.ItemRowBinding
import com.example.githubusers.remote.ListUser
import com.example.githubusers.ui.detailActivity.DetailActivity
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val favoriteList: List<ListUser>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ListUser) {
            binding.tvItemName.text = user.login
            Picasso.get().load(user.avatarUrl).into(binding.imgItemPhoto)

            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.NAMA, user.login)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!favoriteList.isEmpty()) {
            val user = favoriteList[position]
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int {
        return if (favoriteList.isEmpty()) {
            // Jika daftar favorit kosong, kembalikan jumlah item menjadi 1 untuk menampilkan pesan kosong
            1
        } else {
            favoriteList.size
        }
    }
}

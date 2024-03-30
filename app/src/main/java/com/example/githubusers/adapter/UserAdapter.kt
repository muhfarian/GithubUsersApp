package com.example.githubusers.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.ui.detailActivity.DetailActivity
import com.example.githubusers.databinding.ItemRowBinding
import com.example.githubusers.remote.ListUser
import com.squareup.picasso.Picasso

class UserAdapter : ListAdapter<ListUser, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val bind: ItemRowBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(user: ListUser){
            bind.tvItemName.text = user.login
            Picasso.get().load(user.avatarUrl).into(bind.imgItemPhoto)

            itemView.setOnClickListener {
                val i = Intent(it.context, DetailActivity::class.java)
                i.putExtra(DetailActivity.NAMA, user.login)
                it.context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListUser>() {
            override fun areItemsTheSame(oldItem: ListUser, newItem: ListUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListUser, newItem: ListUser): Boolean {
                return oldItem == newItem
            }

        }
    }
}

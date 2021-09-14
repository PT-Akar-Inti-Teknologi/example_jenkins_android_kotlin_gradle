package com.mobile.android.ui.fragments.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.android.databinding.ItemReposBinding
import com.mobile.android.feature.searchrepository.model.RepoDto

/**
 *  RecyclerView with ListAdapter
 *
 */

class ReposAdapter(
    private val listener: RepoItemTouchListener
) : ListAdapter<RepoDto, ReposAdapter.ViewHolder>(RepoDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemReposBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data = getItem(position))
    }

    class ViewHolder(
        private val binding: ItemReposBinding,
        private val listener: RepoItemTouchListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(data: RepoDto){
            binding.textReposName.text = data.name
            binding.textReposRepo.text = data.owner.url
            binding.textReposOwner.text = data.owner.login
            binding.cardViewReposItem.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    private class RepoDiff: DiffUtil.ItemCallback<RepoDto>(){
        override fun areItemsTheSame(
            oldItem: RepoDto,
            newItem: RepoDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RepoDto,
            newItem: RepoDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}

fun interface RepoItemTouchListener{
    fun onClick(item: RepoDto)
}
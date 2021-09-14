package com.mobile.android.ui.fragments.contributor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.android.databinding.ItemContributorBinding
import com.mobile.android.feature.contributor.model.ContributorDto

/**
 *  RecyclerView with ListAdapter
 *
 */
class ContributorAdapter(
    private val listener: ContributorItemTouchListener
) : ListAdapter<ContributorDto, ContributorAdapter.ViewHolder>(ContributorDiff()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemContributorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data = getItem(position))
    }

    class ViewHolder(
        private val binding: ItemContributorBinding,
        private val listener: ContributorItemTouchListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(data: ContributorDto){
            Glide.with(binding.root.context).load(data.avatarUrl).into(binding.imageProfilePicture)
            binding.textContributorName.text = data.login
            binding.textContributorRepo.text = data.reposUrl
            binding.textContributorRole.text = data.type
            binding.cardViewContributorItem.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    private class ContributorDiff: DiffUtil.ItemCallback<ContributorDto>(){
        override fun areItemsTheSame(
            oldItem: ContributorDto,
            newItem: ContributorDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContributorDto,
            newItem: ContributorDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}

fun interface ContributorItemTouchListener{
    fun onClick(item: ContributorDto)
}
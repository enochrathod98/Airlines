package com.example.airlinespractical.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.airlinespractical.R
import com.example.airlinespractical.databinding.ItemLayoutBinding
import com.example.airlinespractical.models.Data

class AirlinesPagingAdapter : PagingDataAdapter<Data, AirlinesPagingAdapter.AirlinesViewHolder>(
    COMPARATOR
) {

    class AirlinesViewHolder(private val binding: ItemLayoutBinding) :  RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data?) {
            binding.name.text = item?.name ?: ""
            binding.id.text = item?._id.toString()
            binding.trips.text = item?.trips.toString() ?: ""
            binding.slogon.text = item?.airline?.get(0)?.slogan ?: ""
            binding.country.text = item?.airline?.get(0)?.country ?: ""
            binding.head.text = item?.airline?.get(0)?.head_quaters ?: ""
            binding.website.text = item?.airline?.get(0)?.website ?: ""
            Glide.with(itemView.context)
                .load(item?.airline?.get(0)?.logo ?: "")
                .into(binding.logo)
        }

        val airline = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlinesViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AirlinesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AirlinesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(getItem(position))

    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}
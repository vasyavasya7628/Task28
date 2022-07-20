package com.example.task28phones.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task28phones.R
import com.example.task28phones.data.DataPhones
import com.example.task28phones.databinding.ItemPhonesBinding

class PhonesAdapter(private val cellClickListener: (String) -> Unit) :
    ListAdapter<DataPhones, PhonesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesViewHolder {
        return PhonesViewHolder(
            ItemPhonesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhonesViewHolder, position: Int) {
        holder.bind(getItem(position), cellClickListener)
    }
}

class PhonesViewHolder(private val binding: ItemPhonesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DataPhones, cellClickListener: (String) -> Unit) {
        val context = itemView.context
        binding.phones.text =
            context.getString(R.string.displayItem, item.name, item.phone, item.type)
        binding.phones.setOnClickListener {
            cellClickListener(item.phone)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<DataPhones>() {

    override fun areItemsTheSame(oldItem: DataPhones, newItem: DataPhones): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataPhones, newItem: DataPhones): Boolean {
        return oldItem == newItem
    }
}
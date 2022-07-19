package com.example.task28phones.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task28phones.R
import com.example.task28phones.data.DataPhones

class PhonesAdapter(private val cellClickListener: (String) -> Unit) :
    ListAdapter<DataPhones, PhonesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhonesViewHolder(inflater.inflate(R.layout.item_phones, parent, false))
    }

    override fun onBindViewHolder(holder: PhonesViewHolder, position: Int) {
        holder.bind(getItem(position), cellClickListener)
    }
}

class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val phonesView: TextView = itemView.findViewById(R.id.phones)

    fun bind(item: DataPhones, cellClickListener: (String) -> Unit) {
        val context = itemView.context
        phonesView.text = context.getString(R.string.displayItem, item.name, item.phone, item.type)
        phonesView.setOnClickListener {
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
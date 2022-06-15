package com.example.task28phones

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PhonesListAdapter : ListAdapter<DataPhones, ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.item_phones, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val phonesView: TextView = itemView.findViewById(R.id.phones)

    fun bind(item: DataPhones) {
        val context = itemView.context
        phonesView.text = context.getString(R.string.displayItem, item.name, item.phone, item.type)
        phonesView.setOnClickListener {
            makeCall(phonesView.context, item.phone)
        }
    }

    private fun makeCall(context: Context, mob: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)

            intent.data = Uri.parse("tel:$mob")
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                context,
                "Unable to call at this time", Toast.LENGTH_SHORT
            ).show()
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<DataPhones>() {

    override fun areItemsTheSame(oldItem: DataPhones, newItem: DataPhones): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: DataPhones, newItem: DataPhones): Boolean {
        return oldItem == newItem
    }
}
package com.example.splitexpenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val context: Context, private var viewModel: NameViewModel) : RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.name_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.name, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var c = 0
        for(i in viewModel.transactions.value!!) {
            if(c == position) {
                holder.textView.text = i
                break
            }
            c++
        }
    }

    override fun getItemCount() = viewModel.transactions.value!!.size
}
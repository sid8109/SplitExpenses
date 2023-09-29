package com.example.splitexpenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class CheckBoxAdapter(private val context: Context, private var viewModel: NameViewModel) : RecyclerView.Adapter<CheckBoxAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_row, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CheckBoxAdapter.ItemViewHolder, position: Int) {
        var idx = 0
        for(i in viewModel.names.value!!.keys) {
            if(idx == position){
                holder.checkbox.setOnClickListener {
                    if (!holder.checkbox.isChecked) {
                        viewModel.spentOn.value!!.remove(holder.checkbox.text.toString())
                    } else {
                        viewModel.spentOn.value!!.add(holder.checkbox.text.toString())
                    }
                }
                holder.checkbox.text = i
                break
            }
            idx++
        }
    }

    override fun getItemCount() = viewModel.names.value!!.size
}
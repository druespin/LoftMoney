package com.example.loftmoney.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.ChargeModel
import com.example.loftmoney.R

class ItemsAdapter(var itemsList: ArrayList<ChargeModel>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    fun setNewData(newData: List<ChargeModel>) {
        itemsList.clear()
        itemsList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(item: ChargeModel) {
        itemsList.add(0, item)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val itemName: TextView = v.findViewById(R.id.item_name)
        val itemPrice: TextView = v.findViewById(R.id.item_price)

        fun bind(chargeModel: ChargeModel) {
            itemName.text = chargeModel.chargeName
            itemPrice.text = chargeModel.chargePrice
        }
    }
}
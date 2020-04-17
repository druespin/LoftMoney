package com.example.loftmoney.adapter

import android.content.res.Resources
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.item_model.ItemModel
import com.example.loftmoney.R
import kotlinx.android.synthetic.main.item_layout.view.*

class ItemsAdapter(private val itemsList: ArrayList<ItemModel>,
                   private val listener: ItemClickListener) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val mSelectedItems = SparseBooleanArray()

    fun clearAllSelections() {
        mSelectedItems.clear()
        notifyDataSetChanged()
    }

    fun toggleItem(position: Int) {
        mSelectedItems.put(position, !mSelectedItems.get(position))
        notifyDataSetChanged()
    }

    fun clearItemSelection(position: Int) {
        mSelectedItems.put(position, false)
        notifyDataSetChanged()
    }

    fun countSelected(): Int {
        var number = 0
        for (i in 0..mSelectedItems.size()) {
            if (mSelectedItems.get(i)) {
                number++
            }
        }
        return number
    }

    fun setNewData(newData: List<ItemModel>) {
        itemsList.clear()
        itemsList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(item: ItemModel) {
        itemsList.add(0, item)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList[position], mSelectedItems.get(position))
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener, View.OnLongClickListener {

        private val itemName = view.item_name
        private val itemPrice = view.item_price

        fun bind(itemModel: ItemModel, isSelected: Boolean) {
            itemName.text = itemModel.chargeName
            itemPrice.text = itemModel.chargePrice
            itemView.isSelected = isSelected

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onItemLongClick(adapterPosition)
            return true
        }
    }
}
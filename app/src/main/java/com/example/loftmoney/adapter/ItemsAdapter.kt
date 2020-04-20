package com.example.loftmoney.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.item_model.ItemModel
import com.example.loftmoney.R
import kotlinx.android.synthetic.main.item_layout.view.*

class ItemsAdapter(private val itemsList: ArrayList<ItemModel>,
                   private val listener: ItemClickListener) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val mSelectedItems = SparseBooleanArray()

    fun toggleItemSelection(position: Int) {
        mSelectedItems.put(position, !mSelectedItems.get(position))
        notifyDataSetChanged()
    }

    fun clearAllSelections() {
        mSelectedItems.clear()
        notifyDataSetChanged()
    }

    fun countSelected(): Int {
        var number = 0
        for (index in 0..mSelectedItems.size()) {
            if (mSelectedItems.get(index)) {
                number++
            }
        }
        return number
    }

    fun getSelectedItemIds(): List<Int> {
        val mSelectedItemIds = ArrayList<Int>()
        for (index in itemsList.indices) {
            if (mSelectedItems[index]) {
                mSelectedItemIds.add(itemsList[index].dataId)
            }
        }
        return mSelectedItemIds
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

    fun removeItem(item: ItemModel) {
        itemsList.remove(item)
        notifyDataSetChanged()
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

        var itemId: Int? = null
        private val itemName = view.budget_item_name
        private val itemPrice = view.budget_item_price

        fun bind(itemModel: ItemModel, isSelected: Boolean) {
            itemId = itemModel.dataId
            itemName.text = itemModel.itemName
            itemPrice.text = itemModel.itemPrice
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
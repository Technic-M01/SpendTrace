package com.technic.spendtrace.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technic.spendtrace.R
import com.technic.spendtrace.database.PurchaseItem
import com.technic.spendtrace.ui.dashboard.PurchaseListAdapter.PurchaseViewHolder
import com.technic.spendtrace.utils.Common

class PurchaseListAdapter: ListAdapter<PurchaseItem, PurchaseViewHolder>(PurchaseComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        return PurchaseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class PurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnLongClickListener {
        private val purchaseVendorTv: TextView = itemView.findViewById(R.id.tv_vendor)
        private val purchasePriceTv: TextView = itemView.findViewById(R.id.tv_price)
        private val purchaseDateTv: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(item: PurchaseItem) {
            purchaseVendorTv.text = item.vendor
            purchaseDateTv.text = item.date
            purchasePriceTv.text = Common.formatPrice(item.price)
        }

        override fun onLongClick(v: View?): Boolean {

            return true
        }

        companion object {
            fun create(parent: ViewGroup): PurchaseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_purchase_item, parent, false)
                return PurchaseViewHolder(view)
            }
        }
    }


    //ToDo: test if we can compare more than a single column info
    class PurchaseComparator : DiffUtil.ItemCallback<PurchaseItem>() {
        override fun areContentsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
            return oldItem.entryID == newItem.entryID
        }

        override fun areItemsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
            return oldItem == newItem
        }
    }

}
package com.kay.samplearch.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import java.util.ArrayList

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {
    val items: MutableList<T> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.show(items[position], position, itemCount)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open class ViewHolder<T> : RecyclerView.ViewHolder, LayoutContainer {
        override val containerView: View = this.itemView

        constructor(layoutId: Int, viewGroup: ViewGroup): super(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))
        constructor(itemView: View): super(itemView)

        open fun show(data: T, position: Int, totalSize: Int) {}
    }
}
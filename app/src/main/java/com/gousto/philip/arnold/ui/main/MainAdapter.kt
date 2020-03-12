package com.gousto.philip.arnold.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gousto.philip.arnold.R
import com.gousto.philip.arnold.databinding.ItemProductBinding
import com.gousto.philip.arnold.storage.StoredData

class MainAdapter(
        val screenWidth: Int,
        val onItemClicked: (item: StoredData) -> Unit
    ) : RecyclerView.Adapter<MainAdapter.ProductViewHolder>() {
    // Our data list is going to be notified when we assign a new list of data to it
    private var dataList: ArrayList<StoredData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_product, parent, false
        )
        val holder = ProductViewHolder(binding, screenWidth)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked.invoke(dataList[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // Verify if position exists in list
        if (position != RecyclerView.NO_POSITION) {
            val data: StoredData = dataList[position]
            holder.bind(data)
        }
    }

    // Update recyclerView's data
    fun updateData(newDataList: List<StoredData>) {
        with(dataList) {
            clear()
            addAll(newDataList)
        }
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: ItemProductBinding, val screenWidth: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StoredData) {
            with(binding) {
                title.text = data.title
                if (data.list_price != "0.00") {
                    price.text = price.context.getString(R.string.listPrice, data.list_price)
                }
                data.images?.a200?.src?.let { src ->
                    val scaledWidth = screenWidth.toFloat() * 0.8
                    val newWidth = (scaledWidth / 100).toInt() * 100
                    val newUrl = src.replace(
                        "x200.jpg", "x$newWidth.jpg"
                    )
                    // Load images using Glide library
                    Glide
                        .with(itemView.context)
                        .load(newUrl)
                        .fitCenter()
                        .into(itemImageView)
                    itemImageView.visibility = View.VISIBLE
                } ?: run {
                    itemImageView.visibility = View.GONE
                }
            }
        }
    }
}
package com.toni.hosteleriatfg.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.data.model.Restaurant

class ProductAdapter(
    private val restaurant: Restaurant,
    private val fragmentManager: FragmentManager,
) : RecyclerView.Adapter<ViewHolderProduct>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_product, parent, false)
        return ViewHolderProduct(view, fragmentManager) // Pasar el FragmentManager al ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        val item = restaurant.productsList[position]
        holder.bind(item)
    }

    override fun getItemCount() = restaurant.productsList.size
}
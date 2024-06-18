package com.toni.hosteleriatfg.app.pedidos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.OrderItem
import com.toni.hosteleriatfg.data.model.Product

class OrderItemAdapter(
    val listaOrderItem: MutableList<OrderItem>,
    val listaProductos: MutableList<Product>,
    val conexion: Conexion
) : RecyclerView.Adapter<ViewHolderOrderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOrderItem {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_order_item, parent, false)
        return ViewHolderOrderItem(view, listaProductos, conexion)
    }

    override fun onBindViewHolder(holder: ViewHolderOrderItem, position: Int) {
        val item = listaOrderItem[position]
        holder.bind(item)
    }

    override fun getItemCount() =listaOrderItem.size

}
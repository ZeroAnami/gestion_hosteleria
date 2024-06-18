package com.toni.hosteleriatfg.app.pedidos.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.OrderItem
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.databinding.ViewItemOrderItemBinding
import com.toni.hosteleriatfg.util.SIMBOLO_MONEDA

class ViewHolderOrderItem(
    val view: View,
    val listaProductos: MutableList<Product>,
    val conexion: Conexion
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemOrderItemBinding

    @SuppressLint("SetTextI18n")
    fun bind(orderItem: OrderItem) {
        binding = ViewItemOrderItemBinding.bind(view)
        binding.textViewCantidadItemHolder.text = orderItem.cantidad.toString()
        val product = listaProductos.first{ it -> it.id == orderItem.producto}
        binding.textViewPrecioItemHolder.text = (String.format("%.2f", product.precio)+SIMBOLO_MONEDA).replace(".",",")
        binding.textViewProductoItemHolder.text = product.nombre
        binding.textViewUsuariosOrderItem.text = conexion.userList.filter { it -> it.id in orderItem.users }.map{it.nombre}.joinToString(separator = ", ")
    }
}
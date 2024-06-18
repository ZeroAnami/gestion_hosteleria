package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.app.main.dialog.AddOrderItemDialog
import com.toni.hosteleriatfg.app.main.dialog.PutUserNameDialog
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.Order
import com.toni.hosteleriatfg.data.model.OrderItem
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.databinding.ViewItemProductBinding
import com.toni.hosteleriatfg.util.SIMBOLO_MONEDA

class ViewHolderProduct(
    private val view: View,
    private val fragmentManager: FragmentManager, // Recibir el FragmentManager en el constructor
    private val conexion: Conexion,
    private val microPedidos: MutableList<OrderItem>
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemProductBinding

    @SuppressLint("SetTextI18n")
    fun bind(item: Product) {
        binding = ViewItemProductBinding.bind(view)
        binding.textProductName.text = item.nombre
        binding.textProductPrice.text = (String.format("%.2f", item.precio)+SIMBOLO_MONEDA).replace(".",",")
        binding.ccViewItemProduct.setOnClickListener {
            val dialog = AddOrderItemDialog(
                conexion,
                item,
                microPedidos
            ).show(fragmentManager, "dialog")
        }
    }
}
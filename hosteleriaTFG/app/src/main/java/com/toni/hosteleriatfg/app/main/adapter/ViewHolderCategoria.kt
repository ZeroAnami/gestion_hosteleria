package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.data.model.Categoria
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.data.model.Restaurant
import com.toni.hosteleriatfg.databinding.ViewItemCategoriaBinding
import com.toni.hosteleriatfg.databinding.ViewItemProductBinding
import com.toni.hosteleriatfg.util.SIMBOLO_MONEDA

class ViewHolderCategoria(
    val view: View,
    val listaProductos: MutableList<Product>,
    val restaurante:Restaurant,
    val adapterProduct: ProductAdapter
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemCategoriaBinding

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    fun bind(item: Categoria) {
        binding = ViewItemCategoriaBinding.bind(view)
        binding.textCategoriaName.text = item.nombre
        binding.ccViewItemCategoria.setOnClickListener{
            listaProductos.clear()
            listaProductos.addAll(restaurante.productsList.filter { x -> x.categoria == item.id })
            adapterProduct.actualizarProductos()
        }
    }
}
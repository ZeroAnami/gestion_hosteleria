package com.toni.hosteleriatfg.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.data.model.Restaurant
import com.toni.hosteleriatfg.data.model.User

class CategoriaAdapter(
    private val restaurant: Restaurant,
    private val listaProductos: MutableList<Product>,
    val adapterProduct: ProductAdapter
) : RecyclerView.Adapter<ViewHolderCategoria>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategoria {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_categoria, parent, false)
        return ViewHolderCategoria(view, listaProductos, restaurant, adapterProduct) // Pasar el FragmentManager al ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderCategoria, position: Int) {
        val item = restaurant.categoriasList[position]
        holder.bind(item)
    }

    override fun getItemCount() = restaurant.categoriasList.size

}
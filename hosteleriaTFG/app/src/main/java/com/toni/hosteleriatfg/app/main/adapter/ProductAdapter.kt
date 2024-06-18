package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.data.model.*

class ProductAdapter(
    private val conexion: Conexion,
    private var listaProductos: MutableList<Product>,
    private val listaEtiqueta: MutableList<Etiqueta>,
    private val listaEtiquetaMarcados: MutableList<Boolean>,
    private val fragmentManager: FragmentManager,
    private val microPedidos: MutableList<OrderItem>
) : RecyclerView.Adapter<ViewHolderProduct>() {

    private var listaProductosFiltrada = listaProductos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProduct {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_product, parent, false)
        return ViewHolderProduct(view, fragmentManager, conexion, microPedidos) // Pasar el FragmentManager al ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        val item = listaProductosFiltrada[position]
        holder.bind(item)
    }

    override fun getItemCount() = listaProductosFiltrada.size

    @SuppressLint("NotifyDataSetChanged")
    fun actualizarProductos(){
        val listaEtiquetasAFiltrar = listaEtiqueta.filterIndexed { index, _ -> listaEtiquetaMarcados.getOrNull(index) == true }.map{it.id!!}
        if(listaEtiquetasAFiltrar.isNotEmpty()){
            listaProductosFiltrada = listaProductos.filter{it -> listaEtiquetasAFiltrar.all { it2 -> it2 in it.etiquetas}}.toMutableList()
        } else {
            listaProductosFiltrada = listaProductos
        }
        notifyDataSetChanged()
    }
}
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

class EtiquetasFilterAdapter(
    private val restaurant: Restaurant,
    private val listaEtiquetasMarcadas: MutableList<Boolean>,
    val adapterProduct: ProductAdapter
) : RecyclerView.Adapter<ViewHolderEtiquetaFilter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEtiquetaFilter {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_etiqueta_filter, parent, false)
        return ViewHolderEtiquetaFilter(view, listaEtiquetasMarcadas, adapterProduct)
    }

    override fun onBindViewHolder(holder: ViewHolderEtiquetaFilter, position: Int) {
        val item = restaurant.etiquetas[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = restaurant.etiquetas.size

}
package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.databinding.ViewItemEtiquetaFilterBinding

class ViewHolderEtiquetaFilter(
    val view: View,
    private val etiquetasListPressed: MutableList<Boolean>,
    private val productAdapter:ProductAdapter
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemEtiquetaFilterBinding

    @SuppressLint("PrivateResource", "NotifyDataSetChanged")
    fun bind(item: Etiqueta, position: Int) {
        binding = ViewItemEtiquetaFilterBinding.bind(view)
        binding.textEtiquetaName.text = item.nombre
        updateStatePressed(position)
        binding.ccViewItemEtiquetaFilter.setOnClickListener {
            etiquetasListPressed[position] = !etiquetasListPressed[position]
            updateStatePressed(position)
            productAdapter.actualizarProductos()
        }
    }

    @SuppressLint("ResourceAsColor")
    fun updateStatePressed(position: Int){
        val nightModeFlags = view.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if(etiquetasListPressed[position]){
                    binding.ccViewItemEtiquetaFilter.setCardBackgroundColor(view.context.getColor(R.color.textColorDefaultMark))
                }
                else {
                    binding.ccViewItemEtiquetaFilter.setCardBackgroundColor(view.context.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            else -> {
                if(etiquetasListPressed[position]){
                    binding.ccViewItemEtiquetaFilter.setCardBackgroundColor(view.context.getColor(R.color.textColorDefaultMark))
                    binding.textEtiquetaName.setTextColor(view.context.getColor( android.R.color.white))
                }
                else {
                    binding.ccViewItemEtiquetaFilter.setCardBackgroundColor(view.context.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                    binding.textEtiquetaName.setTextColor(view.context.getColor( android.R.color.black))
                }
            }
        }

    }
}
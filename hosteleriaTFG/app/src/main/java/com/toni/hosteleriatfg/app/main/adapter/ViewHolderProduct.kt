package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.databinding.ViewItemProductBinding
import com.toni.hosteleriatfg.util.SIMBOLO_MONEDA

class ViewHolderProduct(
    val view: View,
    private val fragmentManager: FragmentManager, // Recibir el FragmentManager en el constructor
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemProductBinding

    @SuppressLint("SetTextI18n")
    fun bind(item: Product) {
        binding = ViewItemProductBinding.bind(view)
        binding.textProductName.text = item.nombre
        binding.textProductPrice.text = (item.precio.toString()+SIMBOLO_MONEDA).replace(".",",")
    }
}
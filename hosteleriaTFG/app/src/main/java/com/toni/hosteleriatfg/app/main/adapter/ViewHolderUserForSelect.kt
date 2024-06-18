package com.toni.hosteleriatfg.app.main.adapter

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.app.main.dialog.PutUserNameDialog
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.databinding.ViewItemUserBinding
import com.toni.hosteleriatfg.databinding.ViewItemUserForSelectBinding

class ViewHolderUserForSelect(
    val compartir: BooleanRef,
    val view: View,
    val producto: Product,
    private val userListPressed: MutableList<Boolean>,
    private val microPedidos: MutableList<OrderItem>,
    private val unidades: IntRef
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemUserForSelectBinding

    @SuppressLint("PrivateResource")
    fun bind(item: User, position: Int) {
        binding = ViewItemUserForSelectBinding.bind(view)
        binding.textUserNameForSelect.text = item.nombre
        updateStatePressed(position)
        binding.ccViewItemUserForSelect.setOnClickListener {
            if(compartir.value){
                userListPressed[position] = !userListPressed[position]
            } else {
                microPedidos.add(OrderItem(null,null, producto.id, unidades.value, MutableList(1){item.id!!}))
                Toast.makeText(view.context, "${unidades.value} " + (if(unidades.value==1) "unidad añadida" else "unidades añadidas") + " a ${item.nombre}", Toast.LENGTH_LONG).show()
            }
            updateStatePressed(position)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun updateStatePressed(position: Int){
        val nightModeFlags = view.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if(userListPressed[position]){
                    binding.ccViewItemUserForSelect.setCardBackgroundColor(view.context.getColor(R.color.textColorDefaultMark))
                }
                else {
                    binding.ccViewItemUserForSelect.setCardBackgroundColor(view.context.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                }
            }
            else -> {
                if(userListPressed[position]){
                    binding.ccViewItemUserForSelect.setCardBackgroundColor(view.context.getColor(R.color.textColorDefaultMark))
                    binding.textUserNameForSelect.setTextColor(view.context.getColor( android.R.color.white))
                }
                else {
                    binding.ccViewItemUserForSelect.setCardBackgroundColor(view.context.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
                    binding.textUserNameForSelect.setTextColor(view.context.getColor( android.R.color.black))
                }
            }
        }

    }
}
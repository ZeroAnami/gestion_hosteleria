package com.toni.hosteleriatfg.app.main.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.toni.hosteleriatfg.app.main.adapter.UserForSelectAdapter
import com.toni.hosteleriatfg.app.main.adapter.ViewHolderUserForSelect
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.databinding.DialogUsersForSelectBinding
import com.toni.hosteleriatfg.databinding.ViewItemUserForSelectBinding

class AddOrderItemDialog(
    private var conexion: Conexion,
    private val producto: Product,
    private val microPedidos: MutableList<OrderItem>
): DialogFragment() {
    private lateinit var binding: DialogUsersForSelectBinding

    private lateinit var adapter: UserForSelectAdapter
    private val compartir: BooleanRef = BooleanRef(false)
    private val userListPressed: MutableList<Boolean> = MutableList(conexion.userList.size, init = {false})
    private var unidades: IntRef = IntRef(1)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogUsersForSelectBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.textViewProducto.text = producto.nombre
        binding.textViewUnidades.text = unidades.value.toString()
        binding.buttonMasCompartir.visibility = View.INVISIBLE

        adapter = UserForSelectAdapter(compartir, conexion, userListPressed, microPedidos, producto, unidades)
        binding.rvListaUsuariosForSelect.adapter = adapter
        binding.rvListaUsuariosForSelect.layoutManager = GridLayoutManager(context,3)


        binding.switchCompartir.setOnCheckedChangeListener { _, isChecked ->
            compartir.value = isChecked
            if(!isChecked) {
                resetearColor()
                binding.buttonMasCompartir.visibility = View.INVISIBLE
            } else {
                binding.buttonMasCompartir.visibility = View.VISIBLE
            }
        }

        binding.buttonPlus.setOnClickListener {
            unidades.value++
            binding.textViewUnidades.text = unidades.value.toString()
        }

        binding.buttonSubtract.setOnClickListener {
            if(unidades.value>1){
                unidades.value--
                binding.textViewUnidades.text = unidades.value.toString()
            }
        }

        binding.buttonMasCompartir.setOnClickListener {
            if(userListPressed.filter{it -> it}.size < 2){
                Toast.makeText(context, "Para compartir es necesario más de 1 persona", Toast.LENGTH_LONG).show()
            }else{
                microPedidos.add(
                    OrderItem(
                        null,
                        null,
                        producto.id,
                        unidades.value,
                        conexion.userList.filterIndexed { index, _ -> userListPressed.getOrNull(index) == true }.map{it.id!!}
                    )
                )
                resetearColor()
            }

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetearColor() {

        userListPressed.clear()
        userListPressed.addAll(MutableList(conexion.userList.size, init = { false }))
        adapter.notifyDataSetChanged()
    }
}

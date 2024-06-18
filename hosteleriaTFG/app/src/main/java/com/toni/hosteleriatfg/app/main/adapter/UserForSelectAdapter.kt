package com.toni.hosteleriatfg.app.main.adapter

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.data.model.*

class UserForSelectAdapter(
    private val compartir: BooleanRef,
    private val conexion:Conexion,
    private val userListPressed: MutableList<Boolean>,
    private val microPedidos: MutableList<OrderItem>,
    private val producto: Product,
    private val unidades: IntRef
) : RecyclerView.Adapter<ViewHolderUserForSelect>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserForSelect {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_user_for_select, parent, false)
        return ViewHolderUserForSelect(compartir, view, producto, userListPressed, microPedidos, unidades)
    }

    override fun onBindViewHolder(holder: ViewHolderUserForSelect, position: Int) {
        val item = conexion.userList[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = conexion.userList.size

}
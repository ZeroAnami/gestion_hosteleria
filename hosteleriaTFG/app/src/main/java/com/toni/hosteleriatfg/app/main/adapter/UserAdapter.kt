package com.toni.hosteleriatfg.app.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.User

class UserAdapter(
    private val fragmentManager: FragmentManager,
    private val conexion:Conexion
) : RecyclerView.Adapter<ViewHolderUser>(), OnUserNameChangedListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_item_user, parent, false)
        return ViewHolderUser(view, conexion.userList, fragmentManager, this) // Pasar el FragmentManager al ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val item = conexion.userList[position]
        holder.bind(item)
    }

    override fun getItemCount() = conexion.userList.size

    fun addItem(usuarioInsertar: User) {
        conexion.userList.add(0, usuarioInsertar)

        notifyItemInserted(0)
    }

    override fun onUserNameChanged(position: Int, newUser: User) {
        conexion.userList[position] = newUser
        notifyItemChanged(position)
    }
}
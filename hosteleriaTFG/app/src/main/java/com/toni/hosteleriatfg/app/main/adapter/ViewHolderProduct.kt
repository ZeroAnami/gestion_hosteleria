package com.toni.hosteleriatfg.app.main.adapter

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.app.main.dialog.PutUserNameDialog
import com.toni.hosteleriatfg.data.model.User
import com.toni.hosteleriatfg.databinding.ViewItemUserBinding

class ViewHolderUser(
    val view: View,
    private val userList: MutableList<User>,
    private val fragmentManager: FragmentManager, // Recibir el FragmentManager en el constructor
    private val userNameChangedListener: OnUserNameChangedListener
) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: ViewItemUserBinding

    fun bind(item: User) {
        binding = ViewItemUserBinding.bind(view)
        binding.textUserName.text = item.nombre
        binding.ccViewItemUser.setOnClickListener {
            val dialog = PutUserNameDialog(
                item.nombre,
                onSubmitClickListener = { nombre ->
                    userNameChangedListener.onUserNameChanged(userList.indexOf(item), nombre)
                }
            ).show(fragmentManager, "dialog")
        }
    }
}
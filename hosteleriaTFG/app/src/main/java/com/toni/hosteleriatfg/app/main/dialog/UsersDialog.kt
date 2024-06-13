package com.toni.hosteleriatfg.app.main.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.toni.hosteleriatfg.app.main.adapter.UserAdapter
import com.toni.hosteleriatfg.app.main.adapter.ViewHolderUser
import com.toni.hosteleriatfg.app.main.adapter.`interface`.OnUserNameChangedListener
import com.toni.hosteleriatfg.data.model.User
import com.toni.hosteleriatfg.databinding.DialogUsersBinding

class UsersDialog(
    private var listUsers:MutableList<User>
): DialogFragment() {

    private lateinit var binding: DialogUsersBinding

    private lateinit var adapter: UserAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogUsersBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        adapter = UserAdapter(listUsers, parentFragmentManager)
        binding.rvListaUsuarios.adapter = adapter
        binding.rvListaUsuarios.layoutManager = LinearLayoutManager(context)

        binding.buttonMasUsuario.setOnClickListener {

            val dialog = PutUserNameDialog(
                "",
                onSubmitClickListener = {nombre ->
                    adapter.addItem(nombre)
                }
            ).show(parentFragmentManager,"dialog")
            binding.rvListaUsuarios.scrollToPosition(0)
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}

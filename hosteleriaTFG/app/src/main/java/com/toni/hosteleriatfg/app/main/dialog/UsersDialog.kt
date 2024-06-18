package com.toni.hosteleriatfg.app.main.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.toni.hosteleriatfg.app.main.adapter.UserAdapter
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.User
import com.toni.hosteleriatfg.databinding.DialogUsersBinding

class UsersDialog(
    private var conexion: Conexion
): DialogFragment() {

    private lateinit var binding: DialogUsersBinding

    private lateinit var adapter: UserAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogUsersBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        adapter = UserAdapter(parentFragmentManager, conexion)
        binding.rvListaUsuarios.adapter = adapter
        binding.rvListaUsuarios.layoutManager = LinearLayoutManager(context)

        binding.buttonMasUsuario.setOnClickListener {
            val dialog = PutUserNameDialog(
                User(null,conexion.id,""),
                onSubmitClickListener = {user ->
                    adapter.addItem(user)
                    binding.rvListaUsuarios.scrollToPosition(0)
                }
            ).show(parentFragmentManager,"dialog")
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}

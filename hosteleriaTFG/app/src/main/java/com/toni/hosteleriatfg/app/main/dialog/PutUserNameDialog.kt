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
import com.toni.hosteleriatfg.databinding.DialogPutUsersNameBinding
import com.toni.hosteleriatfg.databinding.DialogUsersBinding

class PutUserNameDialog(
    val texto:String,
    private val onSubmitClickListener: (String) -> Unit
): DialogFragment(

) {

    private lateinit var binding: DialogPutUsersNameBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPutUsersNameBinding.inflate(LayoutInflater.from(context))
        binding.textInputUserEditor.setText(texto)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)



        binding.buttonMasUsuarioNombre.setOnClickListener {
            onSubmitClickListener.invoke(binding.textInputUserEditor.text.toString())
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}

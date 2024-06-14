package com.toni.hosteleriatfg.app.main.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.toni.hosteleriatfg.app.main.MainActivity
import com.toni.hosteleriatfg.app.main.adapter.UserAdapter
import com.toni.hosteleriatfg.app.main.adapter.ViewHolderUser
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.ResponseRest
import com.toni.hosteleriatfg.data.model.User
import com.toni.hosteleriatfg.data.service.ConexionService
import com.toni.hosteleriatfg.data.service.RestauranteService
import com.toni.hosteleriatfg.data.service.UserService
import com.toni.hosteleriatfg.databinding.DialogPutUsersNameBinding
import com.toni.hosteleriatfg.databinding.DialogUsersBinding
import com.toni.hosteleriatfg.util.STATUS_OK
import com.toni.hosteleriatfg.util.STATUS_OK_REGISTRO_CREADO
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates

class PutUserNameDialog(
    val user: User,
    private val onSubmitClickListener: (User) -> Unit
): DialogFragment(

) {

    private lateinit var binding: DialogPutUsersNameBinding
    private var disposable: Disposable? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPutUsersNameBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.buttonMasUsuarioNombre.setOnClickListener {
            user.nombre = binding.textInputUserEditor.text.toString()
            if(user.id != null){
                ServiceFactory.configureSubscriber(UserService(requireContext()).modifyUser(user), UserObserver())
            } else {
                ServiceFactory.configureSubscriber(UserService(requireContext()).createUser(user), UserObserver())
            }

            onSubmitClickListener.invoke(user)
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private inner class UserObserver() : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus == STATUS_OK) {
                val userObject: User = Gson().fromJson(t.mensaje, User::class.java)
                user.nombre = userObject.nombre
            } else if (t.codigoStatus == STATUS_OK_REGISTRO_CREADO){
                val userObject: User = Gson().fromJson(t.mensaje, User::class.java)
                user.id = userObject.id
            }
        }

        override fun onError(e: Throwable) {

        }
    }

}

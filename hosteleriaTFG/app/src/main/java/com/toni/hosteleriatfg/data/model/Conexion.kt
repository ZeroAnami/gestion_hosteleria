package com.toni.hosteleriatfg.data.model

import android.os.Parcelable
import java.io.Serializable

data class Conexion(
    val id: Int? = null,
    val idRestaurante: Int? = null,
    val pedidosList: MutableList<Order> = mutableListOf(),
    val userList: MutableList<User> = mutableListOf(),
    val estado: Int? = null,
    val mesa: Int? = null,
) : Serializable
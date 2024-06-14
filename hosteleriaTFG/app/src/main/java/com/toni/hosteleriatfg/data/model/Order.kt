package com.toni.hosteleriatfg.data.model

import java.io.Serializable

data class Order(
    val id: Int? = null,
    val id_conexion: Int? = null,
    val orderItemList: MutableList<OrderItem> = mutableListOf()
) : Serializable
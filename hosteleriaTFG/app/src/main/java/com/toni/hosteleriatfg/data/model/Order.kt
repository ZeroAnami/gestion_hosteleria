package com.toni.hosteleriatfg.data.model

import java.io.Serializable

data class Order(
    var id: Int? = null,
    val idConexion: Int? = null,
    val orderItemList: MutableList<OrderItem> = mutableListOf()
) : Serializable
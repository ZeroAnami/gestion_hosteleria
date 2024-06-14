package com.toni.hosteleriatfg.data.model

import java.io.Serializable

data class OrderItem(
    val id: Int? = null,
    val id_order: Int? = null,
    val producto: Int? = null, //Id del producto
    val cantidad: Int? = null,
    val users: List<Int> = mutableListOf() //Ids de los usuarios
) : Serializable
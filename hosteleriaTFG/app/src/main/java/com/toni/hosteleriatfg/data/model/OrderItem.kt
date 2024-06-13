package com.toni.hosteleriatfg.data.model

data class OrderItem(
    val id: Int,
    val id_order: Int,
    val producto: Int, //Id del producto
    val cantidad: Int,
    val users: List<Int> //Ids de los usuarios
)
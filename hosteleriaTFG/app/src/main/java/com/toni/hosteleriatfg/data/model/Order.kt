package com.toni.hosteleriatfg.data.model

data class Order(
    val id: Int,
    val id_conexion: Int,
    val orderItemList: MutableList<OrderItem>
)
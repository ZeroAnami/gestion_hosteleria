package com.toni.hosteleriatfg.data.model

data class Conexion(
    val id: Int,
    val idRestaurante: Int,
    val pedidosList: MutableList<Order>,
    val userList: MutableList<Order>
)

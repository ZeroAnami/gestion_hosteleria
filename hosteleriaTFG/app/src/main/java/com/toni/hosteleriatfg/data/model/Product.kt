package com.toni.hosteleriatfg.data.model

import java.io.Serializable

data class Product(
    var id: Int? = null,
    var id_restaurante: Int? = null,
    var nombre: String? = null,
    var precio: Double? = null,
    val categoria: Int? = null,
    val etiquetas: MutableList<Int> = mutableListOf()
)  : Serializable


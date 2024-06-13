package com.toni.hosteleriatfg.data.model

data class Restaurant(
    val id:Int,
    val nombre:String,
    val productsList: MutableList<Product>,
    val categoriasList: MutableList<Categoria>,
    val etiquetas: MutableList<Etiqueta>
)

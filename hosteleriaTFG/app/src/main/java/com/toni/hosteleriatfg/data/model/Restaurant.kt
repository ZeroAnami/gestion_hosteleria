package com.toni.hosteleriatfg.data.model

data class Restaurant(
    val id:Int? = null,
    val nombre:String? = null,
    val productsList: MutableList<Product> = mutableListOf(),
    val categoriasList: MutableList<Categoria> = mutableListOf(),
    val etiquetas: MutableList<Etiqueta> = mutableListOf()
)

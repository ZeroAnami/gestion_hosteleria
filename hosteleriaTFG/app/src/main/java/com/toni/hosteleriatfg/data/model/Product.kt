package com.toni.hosteleriatfg.data.model

data class Product(
    var id: Int? = null,
    var nombre: String? = null,
    var precio: Double? = null
) {
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Product
        return id == other.id
    }

    override fun toString(): String {
        return "Product(id=$id, nombre=$nombre, precio=$precio)"
    }
}


package com.toni.hosteleriatfg.data.model

import java.io.Serializable

data class User(
    var id: Int? = null,
    var idConexion: Int? = null,
    var nombre: String? = null
) : Serializable
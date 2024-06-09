package com.toni.hosteleriatfg.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseRest(@Expose @SerializedName("codigoStatus") val codigoStatus: Int,
                        @Expose @SerializedName("mensaje") val mensaje: String,
                        @Expose @SerializedName("descripcion") val descripcion: String)

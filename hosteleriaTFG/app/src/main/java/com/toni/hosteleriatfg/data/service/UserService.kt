package com.toni.hosteleriatfg.data.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.toni.hosteleriatfg.data.HosteleriaTFGService
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.ResponseRest
import io.reactivex.Observable

class ConexionService(val context: Context) {
    fun getConexionByID(id:Int): Observable<ResponseRest> {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).getConexionByID(id)
    }

    fun createConexion(conexion:Conexion): Observable<ResponseRest> {
        val gson = GsonBuilder().serializeNulls().create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).createConexion(conexion)
    }
}

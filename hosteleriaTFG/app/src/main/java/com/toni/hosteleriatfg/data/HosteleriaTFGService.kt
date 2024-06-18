package com.toni.hosteleriatfg.data

import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.ResponseRest
import com.toni.hosteleriatfg.data.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface HosteleriaTFGService {
    companion object {
        const val ENDPOINT = "http://5.224.99.19:40091/"
    }

    @GET("restTFG/api/products/getById")
    fun getProductByID(@Query("id") id:Int): Observable<ResponseRest>

    @GET("restTFG/api/conexion/getById")
    fun getConexionByID(@Query("id") id:Int): Observable<ResponseRest>

    @GET("restTFG/api/restaurants/getById")
    fun getRestauranteByID(@Query("id") id:Int): Observable<ResponseRest>

    @POST("restTFG/api/conexion")
    fun createConexion(@Body conexion: Conexion): Observable<ResponseRest>

    @POST("restTFG/api/users")
    fun createUser(@Body user: User): Observable<ResponseRest>

    @PUT("restTFG/api/users")
    fun modifyUser(@Body user: User): Observable<ResponseRest>

}
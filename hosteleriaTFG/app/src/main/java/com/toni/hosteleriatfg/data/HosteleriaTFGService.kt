package com.toni.hosteleriatfg.data

import com.toni.hosteleriatfg.data.model.ResponseRest
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface HosteleriaTFGService {
    companion object {
        const val ENDPOINT = "http://5.225.1.194:40091/"
    }

    @GET("restTFG/api/products/getById")
    fun getProductByID(@Query("id") id:Int): Observable<ResponseRest>

}
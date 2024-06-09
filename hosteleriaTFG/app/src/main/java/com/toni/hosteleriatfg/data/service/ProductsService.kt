package com.toni.hosteleriatfg.data.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.toni.hosteleriatfg.data.HosteleriaTFGService
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.ResponseRest
import io.reactivex.Observable

class ProductsService(val context: Context) {
    fun getProductByID(id:Int): Observable<ResponseRest> {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).getProductByID(id)
    }
}
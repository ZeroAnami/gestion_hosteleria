package com.toni.hosteleriatfg.data.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.toni.hosteleriatfg.data.HosteleriaTFGService
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Order
import com.toni.hosteleriatfg.data.model.ResponseRest
import io.reactivex.Observable

class OrderService(val context: Context) {
    fun createOrder(order: Order): Observable<ResponseRest> {
        val gson = GsonBuilder().serializeNulls().create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).createOrder(order)
    }
}

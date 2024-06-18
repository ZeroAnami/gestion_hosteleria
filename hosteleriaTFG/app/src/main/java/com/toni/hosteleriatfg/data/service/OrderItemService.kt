package com.toni.hosteleriatfg.data.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.toni.hosteleriatfg.data.HosteleriaTFGService
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Order
import com.toni.hosteleriatfg.data.model.OrderItem
import com.toni.hosteleriatfg.data.model.ResponseRest
import io.reactivex.Observable

class OrderItemService(val context: Context) {
    fun createOrderItem(orderItem: OrderItem): Observable<ResponseRest> {
        val gson = GsonBuilder().serializeNulls().create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).createOrderItem(orderItem)
    }
}

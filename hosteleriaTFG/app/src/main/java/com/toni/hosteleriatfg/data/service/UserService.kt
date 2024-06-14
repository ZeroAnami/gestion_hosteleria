package com.toni.hosteleriatfg.data.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.toni.hosteleriatfg.data.HosteleriaTFGService
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.ResponseRest
import com.toni.hosteleriatfg.data.model.User
import io.reactivex.Observable

class UserService(val context: Context) {
    fun modifyUser(user: User): Observable<ResponseRest> {
        val gson = GsonBuilder().serializeNulls().create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).modifyUser(user)
    }

    fun createUser(user:User): Observable<ResponseRest> {
        val gson = GsonBuilder().serializeNulls().create()
        return ServiceFactory.getRetrofit(context, gson).create(HosteleriaTFGService::class.java).createUser(user)
    }
}

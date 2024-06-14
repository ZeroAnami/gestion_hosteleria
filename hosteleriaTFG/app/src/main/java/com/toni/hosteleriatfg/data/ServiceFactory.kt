package com.toni.hosteleriatfg.data

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

object ServiceFactory {

    const val TIMEOUT = 600L

    fun getRetrofit(context: Context): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        //Hay que añadir el header con el usuario y la contraseña en todas las llamadas al servidor para identificarnos
        val headerInterceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder().addHeader("user", "").addHeader("pass", "")
                .build()
            chain.proceed(request)
        }

        //val lists = listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)

        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            //.connectionSpecs(lists)
            .build()

        val urlServidor = getUrlServidor(context)

        return Retrofit.Builder().baseUrl(urlServidor)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    fun getRetrofit(context: Context, gson: Gson): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        //Hay que añadir el header con el usuario y la contraseña en todas las llamadas al servidor para identificarnos
        val headerInterceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder().addHeader("user", "").addHeader("pass", "")
                .build()
            chain.proceed(request)
        }


        //val lists = listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)


        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            //.connectionSpecs(lists)
            .build()

        val urlServidor = getUrlServidor(context)

        return Retrofit.Builder().baseUrl(urlServidor)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun storeUrlServidor(context: Context?, urlServidor: String?) {
        val editor = context?.getSharedPreferences("urlServidor",Context.MODE_PRIVATE)?.edit()
        editor?.putString("urlServidor", urlServidor)
        editor?.apply()
    }

    private fun getUrlServidor(context: Context?): String {
        val preferences = context?.getSharedPreferences("urlServidor",Context.MODE_PRIVATE)
        return preferences?.getString("urlServidor", "http://5.224.99.19:40091/")?.let { return it }  ?: "http://5.224.99.19:40091/"
    }

    /**
     * Con este método hacemos que una llamada al servidor (el observable) devuelva su respuesta al observer pasado, que realizará las acciones necesarias según sea la respuesta
     */
    fun <T> configureSubscriber(observable: Observable<T>, observer: Observer<T>) {
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(AndroidSchedulers.mainThread()).subscribe(observer)
    }
}
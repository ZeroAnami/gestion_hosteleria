package com.toni.hosteleriatfg.app.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.toni.hosteleriatfg.app.main.adapter.ProductAdapter
import com.toni.hosteleriatfg.app.main.dialog.UsersDialog
import com.toni.hosteleriatfg.app.scanner.ScannerActivity
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.data.service.ProductsService
import com.toni.hosteleriatfg.data.service.RestauranteService
import com.toni.hosteleriatfg.databinding.ActivityMainBinding
import com.toni.hosteleriatfg.util.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainActivity() : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var disposable: Disposable? = null

    private var conexion: Conexion? = null
    private lateinit var restaurante: Restaurant
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conexion = intent.getSerializableExtra("conexion") as? Conexion
        if(conexion == null){
            Toast.makeText(this, "Error al establecer la conexión", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ServiceFactory.configureSubscriber(RestauranteService(this).getRestauranteByID(conexion!!.idRestaurante!!), RestaurantObserver())

        binding.buttonUsers.setOnClickListener {
            UsersDialog(conexion!!).show(supportFragmentManager, "dialog")
        }
    }

    private inner class RestaurantObserver : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus == 200) {
                restaurante = Gson().fromJson(t.mensaje,Restaurant::class.java)

                adapter = ProductAdapter(restaurante, supportFragmentManager)
                binding.rvListaProductos.adapter = adapter
                binding.rvListaProductos.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter.notifyDataSetChanged()
            } else {
                onError(Throwable())
            }
        }

        override fun onError(e: Throwable) {
            Toast.makeText(this@MainActivity, "Error al obtener los datos del restaurante", Toast.LENGTH_LONG).show()

        }
    }


}
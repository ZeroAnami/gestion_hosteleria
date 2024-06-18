package com.toni.hosteleriatfg.app.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.toni.hosteleriatfg.app.pedidos.PedidosActivity
import com.toni.hosteleriatfg.app.main.adapter.CategoriaAdapter
import com.toni.hosteleriatfg.app.main.adapter.EtiquetasFilterAdapter
import com.toni.hosteleriatfg.app.main.adapter.ProductAdapter
import com.toni.hosteleriatfg.app.main.dialog.UsersDialog
import com.toni.hosteleriatfg.app.scanner.ScannerActivity
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.data.service.RestauranteService
import com.toni.hosteleriatfg.databinding.ActivityMainBinding
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainActivity() : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var disposable: Disposable? = null

    private var conexion: Conexion? = null
    private lateinit var restaurante: Restaurant
    private lateinit var adapterProducts: ProductAdapter
    private lateinit var adapterCategorias: CategoriaAdapter
    private lateinit var adapterEtiquetas: EtiquetasFilterAdapter
    private var listaProductosMostrados: MutableList<Product> = mutableListOf()
    private var microPedidos: MutableList<OrderItem> = mutableListOf()
    private lateinit var etiquetasListMarcadas: MutableList<Boolean>
    private val REQUEST_CODE = 40091

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

        binding.buttonFilter.setOnClickListener {
            if(binding.ccPrueba.visibility == View.GONE)
                binding.ccPrueba.visibility = View.VISIBLE
            else
                binding.ccPrueba.visibility = View.GONE
        }

        binding.buttonVerPedido.setOnClickListener {
            val intent = Intent(this, PedidosActivity::class.java)
            with(intent){
                putExtra("conexion", conexion)
                putExtra("orders_items", OrderItemInstance(orderItemList = microPedidos))
                putExtra("restaurante", restaurante)
            }

            startActivityForResult(intent, REQUEST_CODE)
        }

        binding.ccPrueba.visibility = View.GONE
        UsersDialog(conexion!!).show(supportFragmentManager, "dialog")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val conexionAdquirida = data?.getSerializableExtra("conexion") as? Conexion
            val orderItemInstanceAdquirido = data?.getSerializableExtra("orders_items_devuelto") as? OrderItemInstance
            if(orderItemInstanceAdquirido != null && conexionAdquirida != null){
                microPedidos.clear()
                microPedidos.addAll(orderItemInstanceAdquirido.orderItemList)
                conexion = conexionAdquirida
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(true)
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

                etiquetasListMarcadas = MutableList(restaurante.etiquetas.size, init = {false})
                binding.textViewNombreRestaurante.text = restaurante.nombre

                adapterProducts = ProductAdapter(conexion!!, listaProductosMostrados, restaurante.etiquetas, etiquetasListMarcadas, supportFragmentManager, microPedidos)
                binding.rvListaProductos.adapter = adapterProducts
                binding.rvListaProductos.layoutManager = LinearLayoutManager(this@MainActivity)
                adapterProducts.notifyDataSetChanged()

                adapterCategorias = CategoriaAdapter(restaurante,listaProductosMostrados, adapterProducts)
                binding.rvListaCategorias.adapter = adapterCategorias
                binding.rvListaCategorias.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                adapterCategorias.notifyDataSetChanged()

                adapterEtiquetas = EtiquetasFilterAdapter(restaurante, etiquetasListMarcadas, adapterProducts)
                binding.rvListaEtiquetas.adapter = adapterEtiquetas
                binding.rvListaCategorias.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                adapterEtiquetas.notifyDataSetChanged()
            } else {
                onError(Throwable())
            }
        }

        override fun onError(e: Throwable) {
            Toast.makeText(this@MainActivity, "Error al obtener los datos del restaurante", Toast.LENGTH_LONG).show()

        }
    }


}
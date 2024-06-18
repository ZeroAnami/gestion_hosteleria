package com.toni.hosteleriatfg.app.pedidos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.toni.hosteleriatfg.app.main.MainActivity
import com.toni.hosteleriatfg.app.pedidos.adapter.OrderItemAdapter
import com.toni.hosteleriatfg.app.scanner.ScannerActivity
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.*
import com.toni.hosteleriatfg.data.service.ConexionService
import com.toni.hosteleriatfg.data.service.OrderItemService
import com.toni.hosteleriatfg.data.service.OrderService
import com.toni.hosteleriatfg.data.service.RestauranteService
import com.toni.hosteleriatfg.databinding.ActivityPedidosBinding
import com.toni.hosteleriatfg.util.STATUS_OK
import com.toni.hosteleriatfg.util.STATUS_OK_REGISTRO_CREADO
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class PedidosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPedidosBinding
    private var disposable: Disposable? = null

    private var conexion: Conexion? = null
    private var microPedidos: OrderItemInstance? = null
    private var restaurante: Restaurant? = null
    private var verPedidosRealizados: Boolean = false
    private lateinit var orderAEnviar: Order

    private lateinit var adapterOrderItem: OrderItemAdapter
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = intent.getSerializableExtra("conexion") as? Conexion
        microPedidos = intent.getSerializableExtra("orders_items") as? OrderItemInstance
        restaurante = intent.getSerializableExtra("restaurante") as? Restaurant
        if(conexion == null || microPedidos == null || restaurante == null){
            Toast.makeText(this, "Error al acceder a los pedidos", Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.putExtra("orders_items_devuelto", microPedidos) // Aquí pones el valor modificado
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        adapterOrderItem = OrderItemAdapter(microPedidos!!.orderItemList, restaurante!!.productsList, conexion!!)
        binding.rvListaOrderItem.adapter = adapterOrderItem
        binding.rvListaOrderItem.layoutManager = LinearLayoutManager(this)
        adapterOrderItem.notifyDataSetChanged()

        binding.buttonVerPedidosRealizados.setOnClickListener {
            if(verPedidosRealizados){
                verPedidosRealizados = false
                adapterOrderItem = OrderItemAdapter(microPedidos!!.orderItemList, restaurante!!.productsList, conexion!!)
                binding.buttonVerPedidosRealizados.text = "Ver pedidos enviados"
                binding.buttonEnviarPedido.text = "Enviar pedido"
            } else {
                verPedidosRealizados = true
                val pedidosRealizados = mutableListOf<OrderItem>()
                conexion!!.pedidosList.forEach{
                    pedidosRealizados.addAll(it.orderItemList)
                }
                adapterOrderItem = OrderItemAdapter(pedidosRealizados, restaurante!!.productsList, conexion!!)
                binding.buttonVerPedidosRealizados.text = "Ver pedido actual"
                binding.buttonEnviarPedido.text = "Pagar"
            }
            binding.rvListaOrderItem.adapter = adapterOrderItem
            adapterOrderItem.notifyDataSetChanged()
        }

        binding.buttonEnviarPedido.setOnClickListener {
            if(verPedidosRealizados){
                if(conexion!!.pedidosList.isNotEmpty()){
                    ServiceFactory.configureSubscriber(ConexionService(this).finalizarPedido(conexion!!.id!!), ConexionObserver())
                } else {
                    Toast.makeText(this, "No hay pedidos para pagar", Toast.LENGTH_LONG).show()
                }
            }else{
                if(microPedidos!!.orderItemList.isNotEmpty()){
                    orderAEnviar = Order(null,conexion!!.id, mutableListOf())
                    ServiceFactory.configureSubscriber(OrderService(this).createOrder(orderAEnviar), OrderObserver())
                }
            }
        }
    }

    private inner class OrderObserver() : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus != STATUS_OK_REGISTRO_CREADO) {
                conexion!!.pedidosList.remove(orderAEnviar)
                Toast.makeText(this@PedidosActivity, "Error al conectar con el servidor", Toast.LENGTH_LONG).show()
            } else {
                val orderObtenido: Order = Gson().fromJson(t.mensaje,Order::class.java)
                orderAEnviar.id = orderObtenido.id
                for (orderItem in microPedidos!!.orderItemList) {
                    orderItem.idOrder = orderAEnviar.id
                    ServiceFactory.configureSubscriber(OrderItemService(this@PedidosActivity).createOrderItem(orderItem), OrderItemObserver(orderItem))

                }
                orderAEnviar.orderItemList.addAll(microPedidos!!.orderItemList)

                conexion!!.pedidosList.add(orderAEnviar)
                microPedidos!!.orderItemList.clear()
                adapterOrderItem.notifyDataSetChanged()
                Toast.makeText(this@PedidosActivity, "Pedido enviado", Toast.LENGTH_LONG).show()
            }
        }

        override fun onError(e: Throwable) {
            conexion!!.pedidosList.remove(orderAEnviar)
            Toast.makeText(this@PedidosActivity, "Error conectar con el servidor", Toast.LENGTH_LONG).show()
        }
    }

    private inner class OrderItemObserver(val item: OrderItem) : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus == STATUS_OK_REGISTRO_CREADO) {
                val orderItemObtenido: OrderItem = Gson().fromJson(t.mensaje,OrderItem::class.java)
                item.id = orderItemObtenido.id
                Toast.makeText(this@PedidosActivity, "Pedido enviado", Toast.LENGTH_LONG).show()
            }
        }

        override fun onError(e: Throwable) {
        }
    }

    private inner class ConexionObserver() : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus == STATUS_OK) {
                Toast.makeText(this@PedidosActivity, "Gracias por consumir en nuestro local", Toast.LENGTH_LONG).show()
                val intent = Intent(this@PedidosActivity, ScannerActivity::class.java)
                startActivity(intent)
            }
        }

        override fun onError(e: Throwable) {
            Toast.makeText(this@PedidosActivity, "Error conectar con el servidor", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("orders_items_devuelto", microPedidos)
        intent.putExtra("conexion", conexion)// Aquí pones el valor modificado
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
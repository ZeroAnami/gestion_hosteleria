package com.toni.hosteleriatfg.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.gson.Gson
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.data.model.ResponseRest
import com.toni.hosteleriatfg.data.service.ProductsService
import com.toni.hosteleriatfg.databinding.ActivityMainBinding
import com.toni.hosteleriatfg.util.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceFactory.configureSubscriber(ProductsService(this).getProductByID(1), ProductObserver())
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
    }

    private inner class ProductObserver : Observer<ResponseRest> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(t: ResponseRest) {
            if (t.codigoStatus == 200) {
                val producto: Product = Gson().fromJson(t.mensaje,Product::class.java)
                binding.textViewIdProduct.text = producto.id.toString()
                binding.textViewNameProduct.text = producto.nombre.toString()
                binding.textViewPrice.text = producto.precio.toString()

            } else {
                binding.textViewIdProduct.text = "0"
                binding.textViewNameProduct.text = "No cargado"
                binding.textViewPrice.text = "0"
            }
        }

        override fun onError(e: Throwable) {
            binding.textViewIdProduct.text = "0"
            binding.textViewNameProduct.text = "No cargado"
            binding.textViewPrice.text = "0"
        }
    }


}
package com.toni.hosteleriatfg.app.scanner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.MainActivity
import com.toni.hosteleriatfg.data.ServiceFactory
import com.toni.hosteleriatfg.data.model.Conexion
import com.toni.hosteleriatfg.data.model.Product
import com.toni.hosteleriatfg.data.model.ResponseRest
import com.toni.hosteleriatfg.data.service.ConexionService
import com.toni.hosteleriatfg.data.service.ProductsService
import com.toni.hosteleriatfg.databinding.ActivityScannerBinding
import com.toni.hosteleriatfg.util.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.invoke.ConstantCallSite

class ScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScannerBinding
    lateinit var sharedPreferencesRestaurant: SharedPreferences
    private var disposable: Disposable? = null
    private lateinit var idRestaurant:String
    private lateinit var idMesa:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonScan.setOnClickListener{
            startScan()
        }
    }

    private fun startScan(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats("QR_CODE")
        integrator.setPrompt("Escanea el QR de la mesa")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val qrText = result.contents
        try {
            idRestaurant = qrText.split(';')[0]
            idMesa = qrText.split(';')[1]
        } catch (e:Exception){
            Toast.makeText(this@ScannerActivity, "El QR no es correcto", Toast.LENGTH_LONG).show()
            return
        }

        sharedPreferencesRestaurant = this.getSharedPreferences("restaurante$idRestaurant", Context.MODE_PRIVATE)
        var conexionEstablecida = sharedPreferencesRestaurant.getString("conexion",null)

        if(conexionEstablecida.isNullOrEmpty()){
            ServiceFactory.configureSubscriber(
                ConexionService(this).createConexion(Conexion(null, idRestaurant.toInt(), mutableListOf(), mutableListOf(), 0,  idMesa.toInt()))
                , ConexionObserver()
            )
        } else {
            ServiceFactory.configureSubscriber(
                ConexionService(this).getConexionByID(conexionEstablecida.toInt())
                , ConexionObserver()
            )
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
                val conexion: Conexion = Gson().fromJson(t.mensaje,Conexion::class.java)
                if(conexion.estado == 1){ //La conexión cerró
                    ServiceFactory.configureSubscriber(
                        ConexionService(this@ScannerActivity).createConexion(Conexion(null, idRestaurant.toInt(), mutableListOf(), mutableListOf(), 0,  idMesa.toInt()))
                        , ConexionObserver()
                    )
                } else {
                    val intent = Intent(this@ScannerActivity, MainActivity::class.java).apply {
                        putExtra("conexion", conexion)
                    }
                    startActivity(intent)
                }
            } else if (t.codigoStatus == STATUS_OK_REGISTRO_CREADO){
                val conexion: Conexion = Gson().fromJson(t.mensaje,Conexion::class.java)
                with(sharedPreferencesRestaurant.edit()){
                    putString("conexion",conexion.id.toString())
                    apply()
                }

                val intent = Intent(this@ScannerActivity, MainActivity::class.java).apply {
                    putExtra("conexion", conexion)
                }
                startActivity(intent)

            } else {
                Toast.makeText(this@ScannerActivity, "Error conectar con el servidor", Toast.LENGTH_LONG).show()
            }
        }

        override fun onError(e: Throwable) {
            Toast.makeText(this@ScannerActivity, "Error conectar con el servidor", Toast.LENGTH_LONG).show()
        }
    }
}
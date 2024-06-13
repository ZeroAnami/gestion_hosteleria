package com.toni.hosteleriatfg.app.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.zxing.integration.android.IntentIntegrator
import com.toni.hosteleriatfg.R
import com.toni.hosteleriatfg.app.main.MainActivity
import com.toni.hosteleriatfg.databinding.ActivityScannerBinding

class ScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*binding.buttonScan.setOnClickListener{
            startScan()
        }*/

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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

        if(result != null && result.contents != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
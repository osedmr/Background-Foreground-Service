package com.example.backgroundservices

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.backgroundservices.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // foreground services
        binding.foreStart.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                requestPermission()
            }else{
                startForegroundServiceAndNotification()
            }
        }
        binding.foreStop.setOnClickListener {
            Intent(this,SampleForegroundService::class.java).also {
                stopService(it)
                binding.foreground.text="Service Stopped"
            }
        }















      //background services
        binding.start.setOnClickListener {
            Intent(this,SampleService::class.java).also {
                startService(it)
                binding.textView.text="Service Started"
            }
        }
        binding.stop.setOnClickListener {
            Intent(this,SampleService::class.java).also {
                stopService(it)
                binding.textView.text="Service Stopped"
            }
        }
        binding.verigonder.setOnClickListener {
               Intent(this,SampleService::class.java).also {
                   val gidenveri =" Merhaba Osman"
                   it.putExtra("veri",gidenveri)
                   startService(it)
                   binding.textView.text="Veri Gönderildi"
               }
        }
    }


    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startForegroundServiceAndNotification()
                Log.d("osman", "${permissions[0]}izin verildi")
            }
        }else{
            Log.d("osman","${permissions[0]}izin verilmedi")

        }
    }

    private fun startForegroundServiceAndNotification(){
        Intent(this,SampleForegroundService::class.java).also {
            startService(it)
            binding.foreground.text="Service Started"
        }
    }
}
package com.example.backgroundservices

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class SampleForegroundService : Service() {

    private var CHANNEL_ID = "FOREGROUND_SERVES_ID"
    private var CHANNEL_NAME = "FOREGROUND_SERVES_NAME"
    private var FOREGROUND_ID = 1
    private var NOTIFICATION_ID = 2
    private var handler= Handler(Looper.getMainLooper())
    private var runnuble = object : Runnable {
        override fun run() {
            Log.d("SampleForegroundService", "Service is running...")
            handler.postDelayed(this, 1000)
        }

    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        handler.post(runnuble)
        return START_NOT_STICKY
    }


    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)

        }
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("foreground çalışıyor")
            .setSubText("foregroud")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.profile))
            .build()

        startForeground(FOREGROUND_ID,notification)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID,notification)

    }


    override fun onDestroy() {
        handler.removeCallbacks(runnuble)
        Log.d("SampleForegroundService", "Service is stopped")
        super.onDestroy()
    }

}
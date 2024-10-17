package com.example.backgroundservices

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

//Backgorund Servis
class SampleService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            Log.d("SampleService", "Service is running...")
            handler.postDelayed(this, 1000)
        }

    }
    init {
        Log.d("SampleService", "Service çalışıyor")
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val gelenVeri=intent?.getStringExtra("veri")
        gelenVeri?.let {
            Log.d("SampleService",gelenVeri)
        }
        handler.post(runnable)

        //Start_Stıcky -> sistem tarafından sonlandırılırsa ve yeniden oluşturulması istenirse boş bir istekle çağırılır, bu surekli çalışması gereken hızmetler için uygun
        // Start_not_sticky -> sistem tarafından sonlandırılırsa ve tamamlanmmış işler varsa yeniden yaratılmaz.
        //Start_ redeliver_intent -> en son intent ne ise, servis tekrar oluşturuldugunda onstartCommand() çağırılır ve devam ettirilmesi sağlanır
        return START_STICKY


    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        Log.d("SampleService", "Service is stopped")

    }
}
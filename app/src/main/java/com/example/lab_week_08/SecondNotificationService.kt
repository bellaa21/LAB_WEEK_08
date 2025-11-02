package com.example.lab_week_08

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class SecondNotificationService : Service() {

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var serviceHandler: Handler

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        notificationBuilder = startForegroundNotification()

        val thread = HandlerThread("SecondNotificationThread").apply { start() }
        serviceHandler = Handler(thread.looper)
    }

    private fun startForegroundNotification(): NotificationCompat.Builder {
        val pendingIntent = getPendingIntent()
        val channelId = createNotificationChannel()

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Third worker process is done!")
            .setContentText("SecondNotificationService executed successfully.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(false)

        startForeground(NOTIFICATION_ID, builder.build())
        return builder
    }

    private fun getPendingIntent(): PendingIntent {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_IMMUTABLE else 0
        return PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            flag
        )
    }

    private fun createNotificationChannel(): String {
        val channelId = "002"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Second Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = ContextCompat.getSystemService(this, NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
        return channelId
    }

    companion object {
        const val NOTIFICATION_ID = 0xCA8
    }
}

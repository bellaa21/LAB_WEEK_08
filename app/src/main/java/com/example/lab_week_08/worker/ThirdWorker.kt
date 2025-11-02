package com.example.lab_week_08.worker

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.lab_week_08.SecondNotificationService

class ThirdWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Simulasi proses panjang (3 detik)
        Thread.sleep(3000L)

        // Panggil SecondNotificationService setelah proses selesai
        val serviceIntent = Intent(applicationContext, SecondNotificationService::class.java)
        applicationContext.startForegroundService(serviceIntent)

        return Result.success()
    }

    companion object {
        const val INPUT_DATA_ID = "inId"
        const val OUTPUT_DATA_ID = "outId"
    }
}


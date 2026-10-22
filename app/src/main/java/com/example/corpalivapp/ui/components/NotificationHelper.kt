package com.example.corpalivapp.ui.components

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun mostrarNotificacion(context: Context) {
    val channelId = "corpaliv_channel"

    // 1. Crear el canal (Requerido por Android 8.0+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Notificaciones Alba Lab",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Confirmaciones de colaboración"
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // 2. Construir la alerta visual
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info) // Ícono nativo del sistema
        .setContentTitle("¡Aporte Registrado!")
        .setContentText("La colaboración ha sido guardada exitosamente en Alba Lab.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // 3. Mostrarla (Verificando seguridad en Android 13+)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        NotificationManagerCompat.from(context).notify(101, builder.build())
    }
}
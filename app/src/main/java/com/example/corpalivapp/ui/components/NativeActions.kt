package com.example.corpalivapp.ui.components

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun NativeActions() {
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    // Lanzador nativo para la cámara (Devuelve un Bitmap directo, sin configurar XMLs)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón 1: Cámara
            Button(onClick = { cameraLauncher.launch(null) }) {
                Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Cámara")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adjuntar Foto")
            }

            // Botón 2: Marcador Telefónico
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+56912345678") // Número de contacto Alba Lab
                    }
                    context.startActivity(intent)
                }
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Llamar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Llamar")
            }
        }

        // Si el usuario tomó una foto, mostramos la miniatura abajo
        capturedImage?.let { bitmap ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Comprobante adjunto:", color = MaterialTheme.colorScheme.primary)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto capturada",
                modifier = Modifier.size(120.dp).padding(top = 8.dp)
            )
        }
    }
}
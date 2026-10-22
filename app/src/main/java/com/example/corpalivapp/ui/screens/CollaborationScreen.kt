package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corpalivapp.viewmodel.AppViewModel

@Composable
fun CollaborationScreen(
    viewModel: AppViewModel,
    onNavigateToLogin: () -> Unit // Función para ir al segundo formulario
) {
    // Observamos los estados del ViewModel
    val guardadoCorrectamente by viewModel.guardadoCorrectamente.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    // Estados locales para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var requiereCertificado by remember { mutableStateOf(false) } // El Checkbox
    var esAnonimo by remember { mutableStateOf(false) } // El Switch
    val context = LocalContext.current
    // Efecto para limpiar los campos si se guardó con éxito
    LaunchedEffect(guardadoCorrectamente) {
        if (guardadoCorrectamente) {
            nombre = ""
            monto = ""
            fecha = ""
            requiereCertificado = false
            esAnonimo = false
            com.example.corpalivapp.ui.components.mostrarNotificacion(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Aporte Alba Lab",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 1. Campo String (Nombre)
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del donante") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // 2. Campo Int (Monto)
        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto a colaborar ($)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // 3. Campo Date/String (Fecha)
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (DD/MM/AAAA)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // 4. Checkbox (Certificado)
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = requiereCertificado,
                onCheckedChange = { requiereCertificado = it }
            )
            Text("Solicitar certificado de donación")
        }

        // 5. Switch (Anónimo)
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Donación anónima")
            Switch(
                checked = esAnonimo,
                onCheckedChange = { esAnonimo = it }
            )
        }
// Recursos Nativos: Cámara y Teléfono
        com.example.corpalivapp.ui.components.NativeActions()
        // Mensajes de feedback de la lógica (ViewModel)
        if (mensajeError != null) {
            Text(text = mensajeError!!, color = Color.Red, modifier = Modifier.padding(bottom = 12.dp))
        }
        if (guardadoCorrectamente) {
            Text(text = "¡Aporte registrado exitosamente!", color = Color(0xFF4CAF50), modifier = Modifier.padding(bottom = 12.dp))
        }

        // Botón principal
        Button(
            onClick = {
                val montoInt = monto.toIntOrNull() ?: 0
                viewModel.guardarColaboracion(nombre, montoInt, fecha, requiereCertificado, esAnonimo)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = !cargando
        ) {
            if (cargando) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Registrar Colaboración", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón secundario para ir al login (Segundo Formulario)
        TextButton(onClick = onNavigateToLogin) {
            Text("Acceso Administradores Alba Lab")
        }
    }
}
package com.example.corpalivapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpalivapp.data.local.AppDatabase
import com.example.corpalivapp.data.model.AdminUser
import com.example.corpalivapp.data.model.Collaboration
import com.example.corpalivapp.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio: AppRepository

    init {
        val dao = AppDatabase.getInstance(application).appDao()
        repositorio = AppRepository(dao)

        // Creamos un admin por defecto al iniciar la app para que puedas probar el Login
        registrarAdmin("admin", "1234")
    }

    // ================= ESTADOS AL ESTILO DEL PROFESOR =================
    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()

    private val _guardadoCorrectamente = MutableStateFlow(false)
    val guardadoCorrectamente = _guardadoCorrectamente.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError = _mensajeError.asStateFlow()

    private val _adminAutenticado = MutableStateFlow(false)
    val adminAutenticado = _adminAutenticado.asStateFlow()

    val colaboraciones = repositorio.todasLasColaboraciones

    // ================= LÓGICA DEL FORMULARIO 1: COLABORACIONES =================
    fun guardarColaboracion(nombre: String, monto: Int, fecha: String, requiereCertificado: Boolean, esAnonimo: Boolean) {
        viewModelScope.launch {
            _cargando.value = true
            _guardadoCorrectamente.value = false
            _mensajeError.value = null

            try {
                // Validaciones separadas de la interfaz
                if (nombre.isBlank() || monto <= 0 || fecha.isBlank()) {
                    _mensajeError.value = "Por favor, completa todos los campos correctamente."
                    return@launch
                }

                val nuevaColaboracion = Collaboration(
                    name = nombre,
                    amount = monto,
                    date = fecha,
                    requiresCertificate = requiereCertificado,
                    isAnonymous = esAnonimo
                )

                repositorio.insertarColaboracion(nuevaColaboracion)
                _guardadoCorrectamente.value = true

            } catch (e: Exception) {
                _mensajeError.value = "No se pudo guardar la colaboración: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    // ================= LÓGICA DEL FORMULARIO 2: LOGIN ADMIN =================
    private fun registrarAdmin(username: String, passwordRaw: String) {
        viewModelScope.launch {
            try {
                // Ciframos la clave localmente convirtiéndola a Hashcode (MVP)
                val hash = passwordRaw.hashCode().toString()
                val nuevoAdmin = AdminUser(username = username, passwordHash = hash)
                repositorio.registrarAdmin(nuevoAdmin)
            } catch (e: Exception) {
                // Error silencioso si ya existe
            }
        }
    }

    fun loginAdmin(username: String, passwordRaw: String) {
        viewModelScope.launch {
            _cargando.value = true
            _mensajeError.value = null

            try {
                delay(1000) // Simulamos tiempo de carga para mostrar animación
                val admin = repositorio.obtenerAdminPorUsuario(username)
                val hashInput = passwordRaw.hashCode().toString() // Ciframos el intento para comparar

                if (admin != null && admin.passwordHash == hashInput) {
                    _adminAutenticado.value = true
                } else {
                    _mensajeError.value = "Credenciales incorrectas."
                    _adminAutenticado.value = false
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al procesar el inicio de sesión."
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cerrarSesion() {
        _adminAutenticado.value = false
    }

    fun ocultarMensajes() {
        _guardadoCorrectamente.value = false
        _mensajeError.value = null
    }
}
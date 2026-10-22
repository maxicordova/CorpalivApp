package com.example.corpalivapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.corpalivapp.ui.screens.AdminLoginScreen
import com.example.corpalivapp.ui.screens.CollaborationScreen
import com.example.corpalivapp.ui.theme.CorpalivAppTheme // Asegúrate de que este nombre coincida con tu Theme.kt
import com.example.corpalivapp.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorpalivAppTheme {
                // Controlador de navegación para movernos entre pantallas
                val navController = rememberNavController()

                // Instanciamos el ViewModel que gestiona la base de datos
                val viewModel: AppViewModel = viewModel()

                // Definimos el mapa de rutas de la aplicación
                NavHost(navController = navController, startDestination = "collaboration") {

                    // Pantalla 1: Formulario principal de Alba Lab
                    composable("collaboration") {
                        CollaborationScreen(
                            viewModel = viewModel,
                            onNavigateToLogin = {
                                viewModel.ocultarMensajes() // Limpiamos errores antes de cambiar de pantalla
                                navController.navigate("login")
                            }
                        )
                    }

                    // Pantalla 2: Acceso de Administradores
                    composable("login") {
                        AdminLoginScreen(
                            viewModel = viewModel,
                            onNavigateBack = {
                                viewModel.ocultarMensajes()
                                navController.popBackStack() // Volver atrás
                            }
                        )
                    }
                }
            }
        }
    }
}
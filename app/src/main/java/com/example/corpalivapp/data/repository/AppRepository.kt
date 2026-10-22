package com.example.corpalivapp.data.repository

import com.example.corpalivapp.data.local.AppDao
import com.example.corpalivapp.data.model.AdminUser
import com.example.corpalivapp.data.model.Collaboration
import kotlinx.coroutines.flow.Flow

class AppRepository(private val dao: AppDao) {

    // Obtenemos la lista en tiempo real
    val todasLasColaboraciones: Flow<List<Collaboration>> = dao.getAllCollaborations()

    suspend fun insertarColaboracion(colaboracion: Collaboration) {
        dao.insertCollaboration(colaboracion)
    }

    suspend fun registrarAdmin(admin: AdminUser) {
        dao.insertAdmin(admin)
    }

    suspend fun obtenerAdminPorUsuario(username: String): AdminUser? {
        return dao.getAdminByUsername(username)
    }
}
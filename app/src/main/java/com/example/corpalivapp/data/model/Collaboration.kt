package com.example.corpalivapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collaborations")
data class Collaboration(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // Campo de texto
    val amount: Int, // Campo numérico
    val date: String, // Campo de fecha
    val requiresCertificate: Boolean, // Representará el Checkbox
    val isAnonymous: Boolean // Representará el Switch
)
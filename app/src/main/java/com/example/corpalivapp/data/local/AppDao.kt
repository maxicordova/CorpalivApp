package com.example.corpalivapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corpalivapp.data.model.AdminUser
import com.example.corpalivapp.data.model.Collaboration
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollaboration(collaboration: Collaboration)

    @Query("SELECT * FROM collaborations ORDER BY id DESC")
    fun getAllCollaborations(): Flow<List<Collaboration>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: AdminUser)

    @Query("SELECT * FROM admin_users WHERE username = :username LIMIT 1")
    suspend fun getAdminByUsername(username: String): AdminUser?
}
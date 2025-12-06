package com.example.ucp2.Data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.Data.entity.Dosen
import kotlinx.coroutines.flow.Flow


@Dao
interface DosenDao {
    @Query("select * from dosen")
    fun getAllDosen(): Flow<List<Dosen>>

    @Query("SELECT nama FROM dosen")
    suspend fun getAllDosenNames(): List<String> // Mengembalikan daftar nama dosen

    @Insert
    suspend fun insertDosen(
        dosen: Dosen
    )

    @Query("select * from dosen where nama = :nama")
    fun getDosen(nama: String): Flow<Dosen>
}
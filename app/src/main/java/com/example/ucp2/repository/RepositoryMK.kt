package com.example.ucp2.repository

import com.example.ucp2.Data.database.krsDatabase
import com.example.ucp2.Data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMK {
    suspend fun insertMatakuliah(matakuliah: Matakuliah)
    fun getAllMatakuliah(): Flow<List<Matakuliah>>          //mendapatkan semua data dosen dalam bentuk aliran flow
    fun getMatakuliah(kode: String): Flow<Matakuliah>       //mengambil data dosen berdasarkan nama
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)    // menghapus data mk
    suspend fun updateMatakuliah(matakuliah: Matakuliah)    // memperbarui data mk di db

//    fun getMatakuliahJoin(kode: String) : Flow<Matakuliah>

    suspend fun getDosenList(): Flow<List<String>>

//    fun getDosenList(): List<String> {
//        // Ambil daftar dosen dari database
//        return krsDatabase.dosenDao.getAllDosenNames()
//    }
//
//    suspend fun getDosenList(): List<String> {
//        return dosenDao.getAllDosenNames() // Mengambil daftar nama dosen
//    }

}
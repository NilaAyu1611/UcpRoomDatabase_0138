package com.example.ucp2.repository

import com.example.ucp2.Data.dao.DosenDao
import com.example.ucp2.Data.dao.MataKuliahDao
import com.example.ucp2.Data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocalRepositoryMK(
    private val mataKuliahDao: MataKuliahDao,
    private val dosenDao: DosenDao
): RepositoryMK {
    override suspend fun insertMatakuliah(matakuliah: Matakuliah) {
        mataKuliahDao.insertMatakuliah(matakuliah)
    }

    override fun getAllMatakuliah(): Flow<List<Matakuliah>> {       // memanggil fungsi ini untuk mendapatkan semua data mk dalam bentuk flow
        return mataKuliahDao.getAllMatakuliah()
    }

    override fun getMatakuliah(kode: String): Flow<Matakuliah> {       //fungsi untuk mengambil data mk berdasarkan kode
        return mataKuliahDao.getMatakuliah(kode)
    }

    override suspend fun deleteMatakuliah(matakuliah: Matakuliah) {     // menghapus data mk
        mataKuliahDao.deleteMatakuliah(matakuliah)
    }

    override suspend fun updateMatakuliah(matakuliah: Matakuliah) {     // memperbarui data mk di DB
        mataKuliahDao.updateMatakuliah(matakuliah)
    }
    override suspend fun getDosenList(): Flow<List<String>> {
        return flow {
            emit(dosenDao.getAllDosenNames())
        }
    }


}
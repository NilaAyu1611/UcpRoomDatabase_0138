package com.example.ucp2.repository

import com.example.ucp2.Data.dao.DosenDao
import com.example.ucp2.Data.entity.Dosen
import kotlinx.coroutines.flow.Flow

//menghubungkan DosenDao dengan operasi yang didefinisikan dalam RepositoryDsn
class LocalRepositoryDsn (
    private val dosenDao: DosenDao
): RepositoryDsn{
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDosen(nama: String): Flow<Dosen> {
        return dosenDao.getDosen(nama)
    }

//    override suspend fun getDosenList(): List<String> {
//        return dosenDao.getAllDosenNames()                  // Mengembalikan daftar nama dosen sebagai List<String>
//    }
//
//    override suspend fun getDosenList(): List<String> {
//        return dosenDao.getAllDosenNames()
//    }


}
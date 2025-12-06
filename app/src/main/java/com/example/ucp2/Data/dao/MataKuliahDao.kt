package com.example.ucp2.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.Data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Query("select * from matakuliah")
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    @Insert
    suspend fun insertMatakuliah(
        matakuliah: Matakuliah
    )

    @Query("select * from matakuliah where kode = :kode")
    fun getMatakuliah(kode: String): Flow<Matakuliah>

    @Delete
    suspend fun deleteMatakuliah(matakuliah: Matakuliah)

    @Update
    suspend fun updateMatakuliah(matakuliah: Matakuliah)

    @Query("SELECT nama FROM dosen")
    suspend fun getAllDosenNames(): List<String>
//
//    @Query(
//        """SELECT
//            mk.kode,
//            mk.nama AS namaMK,
//            mk.sks AS SKS,
//            mk.semester,
//            mk.jenismk AS jenisMK,
//            d.nama AS namaDosen
//        FROM
//            matakuliah mk
//        INNER JOIN
//            dosen d
//        ON
//            mk.dosenpengampu = d.nidn
//        WHERE
//            mk.kode = :kode
//        ORDER BY
//            mk.nama ASC
//        """
//    )
//    fun getMatakuliahJoin(kode: String): Flow<List<MatakuliahJoinResult>>
}

package com.example.ucp2.Data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.Data.dao.DosenDao
import com.example.ucp2.Data.dao.MataKuliahDao
import com.example.ucp2.Data.entity.Dosen
import com.example.ucp2.Data.entity.Matakuliah

//Mendefinisikan database dengan tabel dosen & matakuliah
@Database(entities = [Dosen::class, Matakuliah::class],       //Daftar tabel dalam DB
    version = 1,
    exportSchema = false)
abstract class krsDatabase():RoomDatabase(){

    abstract fun dosenDao(): DosenDao       // Dao untuk mengakses data di tabel dosen

    abstract fun mataKuliahDao(): MataKuliahDao     // Dao untuk mengakses data di tabel matakuliah

    companion object{
        @Volatile                   //memastikan bahwa nilai variabel instance selalu sama di semua thread
        private var Instance: krsDatabase? = null

        fun getDatabase(context: Context): krsDatabase{
            return (Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    krsDatabase::class.java,        //class DB
                    "krsDatabase"               // nama DB
                )
                    .build().also { Instance = it }
            })
        }
    }

}

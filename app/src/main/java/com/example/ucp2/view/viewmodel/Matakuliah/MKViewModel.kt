package com.example.ucp2.view.viewmodel.Matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.Data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMK
import kotlinx.coroutines.launch


class MKViewModel (private val repositoryMK: RepositoryMK) : ViewModel(){

    var uiState by mutableStateOf(MKUIState())
        private set

    init {
        //mengambil daftar dosen saat VM diinisialisasi
        viewModelScope.launch {
            repositoryMK.getDosenList().collect { dosenNames ->
                // Mengupdate UI state dengan daftar dosen
                uiState = uiState.copy(dosenList = dosenNames)
            }
        }

    }

    fun update(matakuliahEvent: MatakuliahEvent){
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )

    }

    private fun validateFields(): Boolean{
        val event = uiState.matakuliahEvent
        val errorState = FormErrorStateMK (
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenismk = if (event.jenismk.isNotEmpty()) null else "Jenis Mata Kuliah tidak boleh kosong",
            dosenpengampu = if (event.dosenpengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.matakuliahEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMK.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrorStateMK()
                    )
                }
                catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }

            }
        }else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda"
            )

        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }

}



//untuk merubah state/perubahan
data class MKUIState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorStateMK = FormErrorStateMK(),
    val snackBarMessage: String? = null,
    val dosenList: List<String> = emptyList()
)

data class FormErrorStateMK(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenismk: String? = null,
    val dosenpengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null &&
                semester == null && jenismk == null && dosenpengampu == null
    }
}


//menyimpan input form ke dalam entity
fun MatakuliahEvent.toMatakuliahEntity():Matakuliah = Matakuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenismk = jenismk,
    dosenpengampu = dosenpengampu
)


//data class variabel yang menyimpan data input form
data class MatakuliahEvent(
    val kode:String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenismk: String = "",
    val dosenpengampu: String = "",
)
package com.example.ucp2.view.matakuliah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.view.viewmodel.Dosen.FormErrorState
import com.example.ucp2.view.viewmodel.Dosen.HomeDsnViewModel
import com.example.ucp2.view.viewmodel.Matakuliah.FormErrorStateMK
import com.example.ucp2.view.viewmodel.Matakuliah.MKUIState
import com.example.ucp2.view.viewmodel.Matakuliah.MKViewModel
import com.example.ucp2.view.viewmodel.Matakuliah.MatakuliahEvent
import com.example.ucp2.view.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
object DestinasiInsertMK: AlamatNavigasi {
    override val route: String = "insertMK"
}

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MKViewModel = viewModel(factory = PenyediaViewModel.Factory),

) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()



    // Observasi perubahan SnackbarMessage
    LaunchedEffect (uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah",
                titleColor = Color.Cyan,
                iconColor = Color.White
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFF041137)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(1.dp)
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .fillMaxHeight(),
                shape = MaterialTheme.shapes.medium
            ){
                Box(
                    modifier = Modifier
                        .background(Color(0xFFdbf5f3))
                        .fillMaxHeight()
                        .padding(16.dp)
                ){
                    InsertBodyMk(
                        uiState = uiState,
                        onValueChange = { updatedEvent ->
                            viewModel.update(updatedEvent)
                        },
                        onClick = {
                            viewModel.saveData()
                            onNavigate()
                        },

                    )
                }
            }

        }
    }
}

@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    uiState: MKUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 2.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            dosenList = uiState.dosenList,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(53.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2dc3cd),             // Warna latar belakang tombol
            contentColor = Color.Black                          // Warna teks tombol
        )
        ) {
            Text("Simpan",
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace)
        }
    }
}


@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit = {},
    errorState: FormErrorStateMK = FormErrorStateMK(),
    dosenList: List<String>,
    modifier: Modifier = Modifier
) {
    val jenisMkOptions = listOf("Matkul Wajib", "Matkul Peminatan")
    var expanded by remember { mutableStateOf(false) }
    var selectedDosen by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode Mata Kuliah") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = ""
                )
            },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama Mata Kuliah") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = ""
                )
            },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )



        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.sks,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(sks = it))
            },
            label = { Text("SKS Mata Kuliah") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = ""
                )
            },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Jumlah SKS Mata Kuliah") },
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )



        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.semester,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(semester = it))
            },
            label = { Text("Semester Mata Kuliah") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = ""
                )
            },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester Mata Kuliah") },
        )
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )



        Text(text = "Jenis Mata Kuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisMkOptions.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenismk == jenis,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenismk = jenis))
                        },
                    )
                    Text(text = jenis)
                }
            }

        }




        Text(text = "Dosen Pengampu")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedDosen,
            onValueChange = { /* Tidak ada tindakan langsung */ },
            label = { Text("Pilih Dosen Pengampu") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = ""
                )
            },
            readOnly = true,

            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().zIndex(1f)
        ) {
            dosenList.forEach { dosen ->
                DropdownMenuItem(
                    onClick = {
                        selectedDosen = dosen
                        onValueChange(matakuliahEvent.copy(dosenpengampu = dosen))
                        expanded = false
                    },
                    text = { Text(dosen) }
                )
            }
        }
        Text(
            text = errorState.dosenpengampu ?: "",
            color = Color.Red
        )


    }
}



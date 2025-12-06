package com.example.ucp2.view.dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.Data.entity.Dosen
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.view.viewmodel.Dosen.HomeDsnViewModel
import com.example.ucp2.view.viewmodel.Dosen.HomeUiState
import com.example.ucp2.view.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeDsnView(
    viewModel: HomeDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit,
    onAddDsn: () -> Unit = {},
    onDetailClik: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    Scaffold (
        topBar = {
            TopAppBar(
                judul = "Daftar Dosen",
                showBackButton = true,
                onBack = onBack,
                titleColor = Color.Cyan,
                iconColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDsn,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen",
                )
            }
        },
        containerColor = Color(0xFF041137) // Warna latar belakang Scaffold

    ){innerPadding ->
        val homeUiState by viewModel.homeUIState.collectAsState()

        BodyHomeDsnView(
            homeUiState = homeUiState,
            onclick = {
                onDetailClik
            },
            modifier = Modifier.padding(innerPadding).padding(top = 16.dp)
        )
    }
}

@Composable
fun BodyHomeDsnView (
    homeUiState: HomeUiState,
    onclick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }      // Snackbar state
    when {
        homeUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            // Menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)           // Tampilkan Snackbar
                    }
                }
            }
        }

        homeUiState.listDsn.isEmpty() -> {
            // Menampilkan pesan
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosen.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            // Menampilkan daftar dosen
            ListDosen(
                listDsn = homeUiState.listDsn,
                onClik = {
                    onclick(it)
                },
                modifier = modifier
            )
        }
    }

}

@Composable
fun ListDosen(
    listDsn: List<Dosen>,
    modifier: Modifier = Modifier,
    onClik: (String) -> Unit = { }
){
    LazyColumn(
        modifier = modifier.padding(top = 16.dp)
    ) {
        items(
            items = listDsn,
            itemContent = {Dsn ->
                CardDsn(
                    Dsn = Dsn,
                    onClik = {onClik(Dsn.nama)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDsn(
    Dsn: Dosen,
    modifier: Modifier = Modifier,
    onClik: () -> Unit = { }
){
    Card(
        onClick = onClik,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium

    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFdfdfc7), // Warna awal gradien
                            Color(0xFF3cebdb),  // Warna akhir gradien


                        )
                    )
                )
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = Dsn.nama,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = Dsn.nidn,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Filled.Face, contentDescription = "")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = Dsn.jeniskelamin,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }


            }
        }



    }
}

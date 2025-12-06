 package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.view.HomeView
import com.example.ucp2.view.dosen.DestinasiInsert

import com.example.ucp2.view.dosen.HomeDsnView
import com.example.ucp2.view.dosen.InsertDsnView
import com.example.ucp2.view.matakuliah.DetailMKView
import com.example.ucp2.view.matakuliah.HomeMKView
import com.example.ucp2.view.matakuliah.InsertMkView
import com.example.ucp2.view.matakuliah.UpdateMKView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    )
    {
        composable(DestinasiHome.route) {
            HomeView(
                navController = navController, // Pass the navController to HomeView
                modifier = modifier
            )
        }

        // Home Dosen
        composable(DestinasiHomeDosen.route) {
            HomeDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsert.route
        ) {
            InsertDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )

        }



        // Home Mata Kuliah
        composable(DestinasiHomeMK.route) {
            HomeMKView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMK.route}/$kode")
                },
                onAddMk = {
                    navController.navigate(DestinasiInsertMK.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            route = DestinasiInsertMK.route
        ) {
            InsertMkView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )

        }


        // Detail Mata Kuliah
        composable(
            route = DestinasiDetailMK.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailMK.KODE) { type = NavType.StringType })
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMK.KODE)
            kode?.let { kodeArg ->
                DetailMKView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMK.route}/$it")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

        // Update Mata Kuliah
        composable(
            route = DestinasiUpdateMK.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateMK.KODE) { type = NavType.StringType })
        ) {
            UpdateMKView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}


package com.example.citaspedia.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.R
import com.example.citaspedia.ui.theme.main_text

enum class CitasPediaScreen(
    @StringRes val title: Int
) {
    Login(title = R.string.login),
    Principal(title = R.string.principal),
    Citas(title = R.string.citas),
    Expedientes(title = R.string.expedientes),
    Recetas(title = R.string.recetas),
    Pacientes(title = R.string.pacientes),
    Mostrar(title = R.string.pacientes)
}

@Composable
fun CitasPediaApp(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
            CitasPediaTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CitasPediaScreen.Login.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = CitasPediaScreen.Login.name) {
                LoginScreen(
                    onNextButtonClicked = {
                        navController.navigate(CitasPediaScreen.Principal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasPediaScreen.Principal.name) {
                Pantalla_ini(
                    PacienteButtonClicked = {
                        navController.navigate(CitasPediaScreen.Pacientes.name)
                    },
                    ExpedienteButtonClicked = {
                        navController.navigate(CitasPediaScreen.Expedientes.name)
                    },
                    RecetaButtonClicked = {
                        navController.navigate(CitasPediaScreen.Recetas.name)
                    },
                    CitaButtonClicked = {
                        navController.navigate(CitasPediaScreen.Citas.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasPediaScreen.Pacientes.name) {
                Citaspedia(
                    MostrarButtonClicked = {
                        navController.navigate(CitasPediaScreen.Mostrar.name)
                    },
                    CancelarButtonClicked = {
                        navController.navigate(CitasPediaScreen.Principal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasPediaScreen.Expedientes.name) {
                ExpedienteScreen(
                    CancelarButtonClicked = {
                        navController.navigate(CitasPediaScreen.Principal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasPediaScreen.Recetas.name) {
                Recetas(

                )
            }
            composable(route = CitasPediaScreen.Mostrar.name) {


                InterfazList(

                    //pacientes = ()
                    // quantityOptions = DataSource.quantityOptions,
                    /*UpdateButtonClicked = {
                         //viewModel.setQuantity(it)
                         // navController.navigate(CupcakeScreen.Flavor.name)
                     },
                    DeleteButtonClicked = {

                    },
                     modifier = Modifier
                         //.fillMaxSize()
                         //.padding(dimensionResource(R.dimen.padding_medium))*/
                )
            }
            composable(route = CitasPediaScreen.Citas.name) {
                Citas(
                    CancelarButtonClicked = {
                        navController.navigate(CitasPediaScreen.Principal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitasPediaTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_citaspedia),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = main_text,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


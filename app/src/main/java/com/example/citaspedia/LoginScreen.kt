package com.example.citaspedia

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citaspedia.ui.GameViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.ui.Citaspedia
import com.example.citaspedia.ui.theme.main_text
<<<<<<< HEAD
import com.example.citaspedia.ui.Pantalla_ini
import com.example.citaspedia.ui.Recetas
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.Citas
import com.example.citaspedia.ui.Expedientes
import com.example.citaspedia.ui.InterfazList
import com.example.citaspedia.ui.PacienteRepo
import com.example.citaspedia.ui.PacientesItem
import com.example.citaspedia.ui.PacientesList
import com.example.citaspedia.ui.pacienteRead
import com.google.firebase.firestore.ktx.firestore
=======
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.testTag
>>>>>>> 0cbe69709d3f598dd78b1c8c3e9b1665e3f9e519

enum class CitasScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Principal(title = R.string.pantalla_principal),
    Citas(title = R.string.citas),
    Expedientes(title = R.string.expedientes),
    Recetas(title = R.string.recetas),
    Pacientes(title = R.string.pacientes),
    Mostrar(title = R.string.pacientes)
}


@Composable

fun CitasApp(navController: NavHostController = rememberNavController(),
             viewModel: GameViewModel = viewModel()){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CitasScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasScreen.Start.name
    )
    Scaffold(
        topBar = {
            CitaspediaTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )

        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CitasScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = CitasScreen.Start.name) {
                LoginScreen(
                    // quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        //viewModel.setQuantity(it)
                        navController.navigate(CitasScreen.Principal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )

            }
            composable(route = CitasScreen.Principal.name) {
                Pantalla_ini(
                    // quantityOptions = DataSource.quantityOptions,
                    PacienteButtonClicked = {
                         //viewModel.setQuantity(it)
                          navController.navigate(CitasScreen.Pacientes.name)
                     },
                    ExpedienteButtonClicked = {
                         //viewModel.setQuantity(it)
                         navController.navigate(CitasScreen.Expedientes.name)
                     },
                    RecetaButtonClicked = {
                        //viewModel.setQuantity(it)
                        navController.navigate(CitasScreen.Recetas.name)
                    },
                    CitaButtonClicked = {
                        navController.navigate(CitasScreen.Citas.name)

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasScreen.Pacientes.name) {
                Citaspedia(
                    // quantityOptions = DataSource.quantityOptions,
                    MostrarButtonClicked = {

                        navController.navigate(CitasScreen.Mostrar.name)
                    },
                    CancelarButtonClicked = {
                        navController.navigate(CitasScreen.Principal.name)

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasScreen.Expedientes.name) {
                Expedientes(
                    // quantityOptions = DataSource.quantityOptions,
                    /* onNextButtonClicked = {
                         //viewModel.setQuantity(it)
                         // navController.navigate(CupcakeScreen.Flavor.name)
                     },*/
                     CancelarButtonClicked = {
                        navController.navigate(CitasScreen.Principal.name)

                    },
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CitasScreen.Recetas.name) {
                Recetas(
                    // quantityOptions = DataSource.quantityOptions,
                    /* onNextButtonClicked = {
                         //viewModel.setQuantity(it)
                         // navController.navigate(CupcakeScreen.Flavor.name)
                     },
                     modifier = Modifier
                         //.fillMaxSize()
                         //.padding(dimensionResource(R.dimen.padding_medium))*/
                )
            }
            composable(route = CitasScreen.Mostrar.name) {


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
            composable(route = CitasScreen.Citas.name) {


                Citas(

                    //obtenPaciente()
                    // quantityOptions = DataSource.quantityOptions,
                    CancelarButtonClicked = {
                        navController.navigate(CitasScreen.Principal.name)

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )

            }
        }
    }
}
@Composable
fun obtenPaciente() {
    val db = Firebase.firestore

    //val listaPersonas = mutableListOf<Paciente>()

    db.collection("pacientes")//.document("meq4CKpLUagt7z9aHFbO")
        .get()
        .addOnSuccessListener { result ->
            // if (document != null && document.exists()) {
            // Acceder a cada campo
            for (document in result) {
                val pacien = Paciente()
                val resulData = document.data

                pacien.nombre.value = document.getString("Nombre").toString()

                pacien.edad.value =
                    document.getString("Edad").toString() // Convertir a Int si es necesario

                pacien.sexo.value = document.getString("Sexo").toString()

                pacien.responsable.value = document.getString("Responsable").toString()

                pacien.num_telefonico.value = document.getString("Numero").toString()


                PacienteRepo.pacientes.add(pacien)

            }


              //PacientesList(pacientes = listaPersonas)  // Mostrar los valores
            for(paciente in PacienteRepo.pacientes){
Log.w("Listaderepo", paciente.nombre.value)//}
            //} else {
             //   println("No se encontró el documento")
           }
           /* object Repositorio{
               val pacientes= listaPersonas
            }*/
        }
        .addOnFailureListener { exception ->
            println("Error al obtener el documento: $exception")
        }

   // return listaPersonas
}
@Composable

//usuario: user@gmail.com contraseña: 123456
fun LoginScreen( navController: NavHostController = rememberNavController(),
                 viewModel: GameViewModel = viewModel(),
                 onNextButtonClicked: () -> Unit = {},
                 modifier: Modifier = Modifier) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    //@Composable
    fun signIn(email: String, password: String, onNextButtonClicked: () -> Unit = {},) {

        lateinit var firebaseAuth: FirebaseAuth
        lateinit var authStateListener: FirebaseAuth.AuthStateListener
        lateinit var firebaseAnalytics: FirebaseAnalytics
        firebaseAuth= Firebase.auth
        isLoading = true

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {

                    println("inicia sesion")
                    val user = firebaseAuth.currentUser
                    //firebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
                    //navController.navigate(CitasScreen.Pacientes.name)
                    val nombre=user?.email.toString()
                    val texto = "Bienvenido: $nombre"
                    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
                    onNextButtonClicked()
                } else {
                    println(" no inicia sesion")

                    Toast.makeText(context, "Error de email o contraseña.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.inicio_sesion),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("emailTextField")
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("passwordTextField")
        )
        Spacer(modifier = Modifier.height(16.dp))
        //val imageView = findViewById<ImageView>(R.id.imageView)
        Button(
            onClick = { signIn(email, password, onNextButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("loginButton"),
            enabled = !isLoading
        ){
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Login")
            }
        }
    }




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaspediaTopAppBar(
    currentScreen: CitasScreen,
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


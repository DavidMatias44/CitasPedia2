package com.example.citaspedia.ui

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
//import androidx.compose.material3.Text
//import androidx.compose.material3.Text
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.ui.theme.main_text
import com.example.citaspedia.data.Paciente
//import com.example.citaspedia.ui.pacienteRead
import com.google.firebase.firestore.ktx.firestore
import androidx.compose.ui.platform.testTag
import com.example.citaspedia.R
import com.example.citaspedia.data.Usuario
import kotlinx.coroutines.CoroutineScope

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


//usuario: user@gmail.com contraseña: 123456
@Composable
fun LoginScreen(
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var usuario = remember { Usuario() }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            value = usuario.email.value,
            onValueChange = { usuario.email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("emailTextField")
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = usuario.password.value,
            onValueChange = { usuario.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("passwordTextField")
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                usuario.login(context, onNextButtonClicked)
            },
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

package com.example.citaspedia

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.CitasPediaScreen
import com.example.citaspedia.ui.CitasPediaTopAppBar
import com.example.citaspedia.ui.CitaspediaTopAppBar
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PacienteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                mostrarP()

            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun mostrarP(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CitasPediaScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasPediaScreen.Login.name
    )
    val context = LocalContext.current
    var pacientes by remember { mutableStateOf(listOf<Paciente>()) }
    val db = Firebase.firestore

    LaunchedEffect(Unit) {
        try {
            val result = db.collection("pacientes").get().await()
            val pacienteList = result.documents.map { document ->
                Paciente(
                    nombre = mutableStateOf(document.getString("Nombre").orEmpty()),
                    edad = mutableStateOf(document.getString("Edad").orEmpty()),
                    sexo = mutableStateOf(document.getString("Sexo").orEmpty()),
                    responsable = mutableStateOf(document.getString("Responsable").orEmpty()),
                    num_telefonico = mutableStateOf(document.getString("Numero").orEmpty())
                )
            }
            pacientes = pacienteList
          //  println("Datos de citas obtenidos: $citas")
        } catch (e: Exception) {
            println("Error al obtener el documento: $e")
        }
    }

    Scaffold(
        topBar = {
            CitasPediaTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Citas", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(pacientes) { paciente ->
                    PacienteItem(paciente)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { (context as? ComponentActivity)?.finish() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salir")
            }
        }
    }
}

@Composable
fun PacienteItem(paciente: Paciente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${paciente.nombre.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Edad: ${paciente.edad.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Sexo: ${paciente.sexo.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Responsable: ${paciente.responsable.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Número: ${paciente.num_telefonico.value}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

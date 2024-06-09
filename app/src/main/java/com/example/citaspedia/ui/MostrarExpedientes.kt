package com.example.citaspedia.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.MutableState
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
import com.example.citaspedia.MostrarPacienteActivity
import com.example.citaspedia.data.Expediente
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.idd
import com.example.citaspedia.mostrarP
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.math.exp


var expedienteId: MutableState<String> = mutableStateOf("")
class MostrarExpedientesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                MostrarExpedientes()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MostrarExpedientes(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CitasPediaScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasPediaScreen.Login.name
    )
    val context = LocalContext.current
    var expedientes by remember { mutableStateOf(listOf<Expediente>()) }
    val db = Firebase.firestore

    LaunchedEffect(Unit) {
        try {
            val result = db.collection("expedientes").get().await()
            val pacienteList = result.documents.map { document ->
                Expediente(
                    id = mutableStateOf(document.id),
                    paciente_nombre = mutableStateOf(document.getString("paciente_nombre").orEmpty()),
                    motivo_consulta = mutableStateOf(document.getString("motivo_consulta").orEmpty()),
                    exploracion_fisica = mutableStateOf(document.getString("exploracion_fisica").orEmpty()),
                    diagnostico = mutableStateOf(document.getString("diagnostico").orEmpty()),
                    tratamiento = mutableStateOf(document.getString("tratamiento").orEmpty()),
                    examenes_laboratorio = mutableStateOf(document.getString("examenes_laboratorio").orEmpty()),
                    pronostico = mutableStateOf(document.getString("pronostico").orEmpty())
                )
            }
            expedientes = pacienteList
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
                items(expedientes) { expediente ->
                    ExpedienteItem(expediente)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row{
                        Button(
                            onClick = { expedienteDelete(expediente.id.value) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.4f)
                        ) {
                            Text("Eliminar")
                        }
                        Button(
                            onClick = {
                                expedienteId.value = expediente.id.value
                                println("ID: ${expediente.id.value}")
                                val intent = Intent(context, ActualizaExpedienteActivity::class.java)
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.4f)
                        ) {
                            Text("Actualizar")
                        }
                    }
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
fun ExpedienteItem(expediente: Expediente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre del paciente: ${expediente.paciente_nombre.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Motivo de consulta: ${expediente.motivo_consulta.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Exploracion fisica: ${expediente.exploracion_fisica.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Diagnosotico: ${expediente.diagnostico.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tratamiento: ${expediente.tratamiento.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Examenes laboratorio: ${expediente.examenes_laboratorio.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Pronostico: ${expediente.pronostico.value}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


fun expedienteDelete(id:String){
    val db = Firebase.firestore
    db.collection("expedientes").document(id)
        .delete()
        .addOnSuccessListener {
            Log.d("Firestore", "DocumentSnapshot successfully deleted!")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error deleting document", e)
        }


}

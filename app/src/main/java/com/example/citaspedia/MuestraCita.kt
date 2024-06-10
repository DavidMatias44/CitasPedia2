package com.example.citaspedia

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.data.Cita
import com.example.citaspedia.ui.CitasPediaScreen
import com.example.citaspedia.ui.CitasPediaTopAppBar
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.ui.Alignment

class CitasActivity : ComponentActivity() {
    private val dataBase = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                mostrar()

            }
        }
        /*Firebase.analytics.logEvent("app_open") {
            param("message", "Hello, Firebase!")
        }*/
    }
}
var idc: MutableState<String> =  mutableStateOf("")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun mostrar(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CitasPediaScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasPediaScreen.Login.name
    )



    val context = LocalContext.current
    var citas by remember { mutableStateOf(listOf<Cita>()) }
    val db = Firebase.firestore

    LaunchedEffect(Unit) {
        try {
            val result = db.collection("citas").get().await()
            val citaList = result.documents.map { document ->
                Cita(
                    id=mutableStateOf(document.id),
                    fecha = mutableStateOf(document.getString("Fecha").orEmpty()),
                    hora = mutableStateOf(document.getString("Hora").orEmpty()),
                    nombre_paciente = mutableStateOf(document.getString("Nombre").orEmpty())
                )
            }
            citas = citaList
            println("Datos de citas obtenidos: $citas")
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

            LazyColumn  (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 400.dp) // Ajusta la altura máxima según tus necesidades
            ){
                items(citas) { cita ->
                    CitaItem(cita)
Row {


    Button(
        onClick = { citaDelete(cita.id.value,context) },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(0.4f)
    ) {
        Text("Eliminar")
    }
    val mostrarCitas: () -> Unit = {
        // (context as? ComponentActivity)?.finish()
        idc.value=cita.id.value
        val intent = Intent(context, MostrarCitaActivity::class.java)
        context.startActivity(intent)
    }

    Button(
        onClick = mostrarCitas,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier    .weight(1f)
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
fun CitaItem(cita: Cita) {
    var currentDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    // LaunchedEffect se activa cada vez que cambia el estado de la pantalla
    LaunchedEffect(Unit) {
        while (true) {
            currentDateTime = LocalDateTime.now()
            delay(1000) // Espera 1 segundo antes de actualizar de nuevo
        }
    }

    // Formatear la fecha
    val formattedDate =  formatDate(currentDateTime)

    // Formatear la hora
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val formattedTime =  currentDateTime.format(timeFormatter)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if((formattedDate.substring(3, 5)==cita.fecha.value.substring(3,5))&&(formattedDate.substring(0, 2)==cita.fecha.value.substring(0,2))&&(formattedDate.substring(6, 10)==cita.fecha.value.substring(6,10))){
                Box(
                    modifier = Modifier.fillMaxWidth()
                )  {
                    Text(
                        text = "Pendiente para hoy",


                            fontSize = 24.sp, // Tamaño del texto
                            color = Color.Red, // Color del texto
                            modifier = Modifier.align(Alignment.Center)

                    )
                }
            }
            Text(text = "Nombre: ${cita.nombre_paciente.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Fecha: ${cita.fecha.value}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Hora: ${cita.hora.value}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun citaDelete(id:String,context: Context){
    val db = Firebase.firestore
    db.collection("citas").document(id)
        .delete()
        .addOnSuccessListener {
            Log.d("Firestore", "DocumentSnapshot successfully deleted!")
            Toast.makeText(context, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show()

        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error deleting document", e)
            Toast.makeText(context, "Fallo al eliminar", Toast.LENGTH_SHORT).show()

        }


}
fun formatDate(date: LocalDateTime): String {
    val dayOfMonth = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth.toString()
    val month = if (date.monthValue < 10) "0${date.monthValue}" else date.monthValue.toString()
    val year = date.year
    return "$dayOfMonth/$month/$year"
}

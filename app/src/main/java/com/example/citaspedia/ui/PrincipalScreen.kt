package com.example.citaspedia.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citaspedia.CitasActivity
import com.example.citaspedia.MostrarPacienteActivity
import com.example.citaspedia.PacienteActivity
import com.example.citaspedia.PacienteItem
import com.example.citaspedia.R
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.idd
import com.example.citaspedia.pacienteDelete
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


var pacienteId: MutableState<String> =  mutableStateOf("")
var cont = 0
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pantalla_ini(
    PacienteButtonClicked: () -> Unit = {},
    ExpedienteButtonClicked: () -> Unit = {},
    RecetaButtonClicked: () -> Unit = {},
    CitaButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    // Mantener el estado de la fecha y hora actual
    var currentDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    // LaunchedEffect se activa cada vez que cambia el estado de la pantalla
    LaunchedEffect(Unit) {
        while (true) {
            currentDateTime = LocalDateTime.now()
            delay(1000) // Espera 1 segundo antes de actualizar de nuevo
        }
    }

    // Formatear la fecha
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = formatDate(currentDateTime)

    // Formatear la hora
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val formattedTime =  currentDateTime.format(timeFormatter)

    var pacientesList by remember { mutableStateOf(listOf<Paciente>()) }
    var inputNombre by remember { mutableStateOf("") }
    var isNombreFound by remember { mutableStateOf(false) }
    var pacientesEncontrados by remember {
        mutableStateOf<List<Paciente>>(emptyList())
    }

    var db = Firebase.firestore
    var expanded by remember { mutableStateOf(false) }
    val mostrarPacientes: () -> Unit = {
        val intent = Intent(context, PacienteActivity::class.java)
        context.startActivity(intent)
    }

    val mostrarCitas: () -> Unit = {
        val intent = Intent(context, CitasActivity::class.java)
        context.startActivity(intent)
    }
    val mostrarExpedientes:() -> Unit ={
        val intent = Intent(context, MostrarExpedientesActivity::class.java)
        context.startActivity(intent)
    }
    val items = listOf("Registrar un paciente" to  PacienteButtonClicked,
        "Ver todos los pacientes" to mostrarPacientes, "Registrar una cita" to CitaButtonClicked,
        "Ver agenda de citas" to mostrarCitas, "Elaborar un expediente" to ExpedienteButtonClicked,
        "Ver todos los expedientes" to mostrarExpedientes  )
    var selectedItem by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val result = db.collection("pacientes").get().await()
            val pacientes = result.documents.map { document ->
                Paciente(
                    id = mutableStateOf(document.id),
                    nombre = mutableStateOf(document.getString("Nombre").orEmpty()),
                    edad = mutableStateOf(document.getString("Edad").orEmpty()),
                    sexo = mutableStateOf(document.getString("Sexo").orEmpty()),
                    responsable = mutableStateOf(document.getString("Responsable").orEmpty()),
                    num_telefonico = mutableStateOf(document.getString("Numero").orEmpty())
                )
            }
            pacientesList = pacientes
        } catch (e: Exception) {
            println("Error al obtener el documento: $e")
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = stringResource(id = R.string.principal),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()

            ) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.TopStart).testTag("Menu")
            ) {
                Text("Menú")
            }
            DropdownMenu(
                expanded = expanded,
                modifier = Modifier.fillMaxWidth(0.8f),
                onDismissRequest = { expanded = false }
            ) {

                items.forEach { (item, action) ->
                    Button(
                        onClick = {
                            action()
                            selectedItem = item
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .align(Alignment.CenterHorizontally)
                            //.testTag("Boton${cont}")

                    ) {
                        Text(text = item, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))


                }

            }
        }
        Spacer(modifier = Modifier.height(150.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("$formattedDate $formattedTime")

            Spacer(modifier = Modifier.height(250.dp))
            TextField(
                value = inputNombre,
                onValueChange = {
                    inputNombre = it
                    isNombreFound = false
                },
                modifier = Modifier.testTag("Buscarcampo")
            )
            Button(
                onClick = {
                    pacientesEncontrados = pacientesList.filter {
                        it.nombre.value.contains(inputNombre, ignoreCase = true)
                    }
                    isNombreFound = pacientesEncontrados.isNotEmpty()

                    if(!isNombreFound){
                        Toast.makeText(context, "No hay un paciente con nombre ${inputNombre}", Toast.LENGTH_SHORT).show()
                    }
                    inputNombre = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("BuscarButton"),
                enabled = inputNombre.isNotEmpty()
            ) {
                Text("Buscar paciente")
            }

            if (isNombreFound) {
                pacientesEncontrados.forEach { paciente ->
                    PacienteItem(paciente = paciente)
                    Row {
                        Button(
                            onClick = { pacienteDelete(paciente.id.value, context ) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.4f)
                        ) {
                            Text("Eliminar")
                        }
                        Button(
                            onClick = {
                                isNombreFound = false
                                pacienteId.value = paciente.id.value
                                Log.d("PACIENTE", pacienteId.value)
                                val intent = Intent(context, MostrarPacienteActivity::class.java)
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
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
        }
    }
}

fun formatDate(date: LocalDateTime): String {
    val dayOfMonth = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth.toString()
    val month = if (date.monthValue < 10) "0${date.monthValue}" else date.monthValue.toString()
    val year = date.year
    return "$dayOfMonth/$month/$year"
}


package com.example.citaspedia.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citaspedia.CitasActivity
import com.example.citaspedia.R
import com.example.citaspedia.data.Cita
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.testTag
import com.example.citaspedia.data.Paciente

@Composable
fun Citas(gameViewModel: GameViewModel =   viewModel(),
    // onNextButtonClicked: (Int) -> Unit,
          modifier: Modifier = Modifier,
          CancelarButtonClicked: () -> Unit = {},
          MostrarButtonClicked:  @Composable () -> Unit = {},
) {
    //val banderanumeros: Boolean=false
    val context = LocalContext.current
    val gameuiState by gameViewModel.uiState.collectAsState()
    val cita = remember { Cita() }
    var mostrarb by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    // Lista de opciones

    var pacientes by remember { mutableStateOf(listOf<Paciente>()) }
    val db = Firebase.firestore

    LaunchedEffect(Unit) {
        try {
            val result = db.collection("pacientes")
                .get().await()
            val pacienteList = result.documents.map { document ->
                Paciente(
                    id= mutableStateOf(document.id),
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


    // Estado para controlar si el menú está expandido o no
    var expanded by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            //CitaspediaTopAppBar()
            Text(
                text = stringResource(id = R.string.citas),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                //.padding(start = 56.dp)

            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth()
                .background(background_form)
        ) {
            Spacer(modifier = Modifier.padding(36.dp))

            Text(
                text = stringResource(id = R.string.fecha_cita),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)

            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(


                value = cita.fecha.value,

                onValueChange = { },
                label = { Text("Fecha") },

                modifier = Modifier
                    .height(75.dp).testTag("prueba"),
                shape = RoundedCornerShape(10.dp),
                readOnly = true,


                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre,
                trailingIcon = {
                    IconButton(onClick = {
                        DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                            val Day = if (selectedDay < 10) "0${selectedDay}" else selectedDay.toString()
                            val Month= if(selectedMonth<9) "0${selectedMonth+1}" else (selectedMonth+1).toString()
                            cita.fecha.value= "$Day/${Month }/$selectedYear"
                        }, year, month, day).show()
                    }, modifier = Modifier
                        .testTag("fechaboton")) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = R.string.hora_cita),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value =cita.hora.value,
                onValueChange = {        },
                label = { Text("Hora") },
                modifier = Modifier
                    .height(75.dp),
                shape = RoundedCornerShape(10.dp),
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_edad,
                trailingIcon = {
                    IconButton(onClick = {
                        TimePickerDialog(context, { _, selectedHour, selectedMinute ->
                            cita.hora.value= "$selectedHour:$selectedMinute"
                        }, hour, minute, true).show()
                    }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Time")
                    }
                }
            )


            Spacer(modifier = Modifier.padding(16.dp))


            Text(
                text = stringResource(id = R.string.nombre_paciente),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Box(modifier=Modifier.padding(24.dp)) {
                OutlinedTextField(
                    value = cita.nombre_paciente.value,
                    onValueChange = { newValue ->
                        cita.nombre_paciente.value = newValue
                    },
                    modifier = Modifier
                        .height(75.dp)
                        //.padding(24.dp)

                        .onFocusChanged { focusState ->
                            expanded = focusState.isFocused
                        },
                    label = { Text("Selecciona una opción") },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    isError = gameuiState.error_responsable,
                    readOnly = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)


                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                    //.padding(start = 16.dp, end = 16.dp)
                ) {
                    pacientes.forEach { opcion ->
                        DropdownMenuItem(
                            {
                                Text(text = opcion.nombre.value)
                            },
                            onClick = {
                                // opcionSeleccionada = opcion
                                cita.nombre_paciente.value =
                                    opcion.nombre.value // Actualiza el valor del TextField al seleccionar una opción
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))
            Row(

            ) {
                Button(
                    onClick =  CancelarButtonClicked,
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = denied_button
                    )
                ) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = { mostrarb=true
                        citaInsert(cita,context) },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Registrar")
                }
                val mostrarCitas: () -> Unit = {
                    val intent = Intent(context, CitasActivity::class.java)
                    context.startActivity(intent)
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick =  mostrarCitas ,
                    shape = RectangleShape,
                    enabled = mostrarb,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Mostrar")
                }

            }
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }

}



fun citaInsert(cita: Cita,context: Context) {
    val db = Firebase.firestore
    val unacita = hashMapOf(
        "Fecha" to cita.fecha.value,
        "Hora" to cita.hora.value,
        "Nombre" to cita.nombre_paciente.value,

        )

// Add a new document with a generated ID
    var id:String=""
    db.collection("citas")
        .add(unacita)
        .addOnSuccessListener {
            // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            // id=documentReference.id
            Toast.makeText(context, "Se registro correctamente", Toast.LENGTH_SHORT).show()

        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
            Toast.makeText(context, "Fallo al registrar", Toast.LENGTH_SHORT).show()

        }

}

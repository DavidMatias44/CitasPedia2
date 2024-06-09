package com.example.citaspedia.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
    var fecha by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var mostrarb by remember { mutableStateOf(true) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)


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


                value = fecha,

                onValueChange = { },
                label = { Text("Fecha") },

                modifier = Modifier
                    .height(75.dp),
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
                           cita.fecha.value= "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        }, year, month, day).show()
                    }) {
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
                //gameViewModel.banderanumeros=true,
                //  gameViewModel.help(),
                value =horaInicio
                ,
                onValueChange = {
                },
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
            OutlinedTextField(
                value = cita.nombre_paciente.value,
                onValueChange = { newValue ->
                    cita.nombre_paciente.value = newValue
                },
                modifier = Modifier
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError =gameuiState.error_responsable
            )

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
                        citaInsert(cita) },
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

fun citaInsert(cita: Cita) {
    //Log.d(TAG, paciente.nombre.toString())
    // Create a new user with a first and last name

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
        .addOnSuccessListener { documentReference ->
            // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            id=documentReference.id

        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }

    // Log.d(TAG,"sss ${id.id}")

}



/*fun mostrar(modifier: Modifier = Modifier) {


    /* Important: It is not a good practice to access data source directly from the UI.
        In later units you will learn how to use ViewModel in such scenarios that takes the
        data source as a dependency and exposes heroes.
         */

    var citas by remember { mutableStateOf(listOf<Cita>()) }

    val db = Firebase.firestore
    var idColeccion:String=""

    val viajesRef = db.collection("viajes")
    //val listaPersonas = PacienteRepo.pacientes
    LaunchedEffect(Unit) {
        db.collection("citas")//.document("meq4CKpLUagt7z9aHFbO")
            .get()
            .addOnSuccessListener { result ->
                val citaList = mutableListOf<Cita>()
                for (document in result) {
                    val cita = Cita()
                    //idColeccion = document.id
                    cita.fecha = document.getString("Fecha").toString()
                    cita.hora = document.getString("Hora").toString()
                    cita.nombre_paciente = document.getString("Nombre").toString()


                    citaList.add(cita)

                }
                citas = citaList

                //PacientesList(pacientes = listaPersonas)  // Mostrar los valores
                //for(paciente in listaPersonas){
               /* for (cita in CitasRepo.citas) {
                    Log.w("Listadecita", cita.fecha)//}
                    Log.w("Listadecita", cita.hora)
                    Log.w("Listadecita", cita.nombre_paciente)
                    //} else {
                    //   println("No se encontró el documento")
                }*/

                //} else {
                //   println("No se encontró el documento")
                //   }
            }
            .addOnFailureListener { exception ->
                println("Error al obtener el documento: $exception")
            }

    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Citas", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(citas) { cita ->
                CitaItem(cita)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }


}

@Composable
fun CitaItem(cita: Cita) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
           // Text(text = "ID: ${viaje.id}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Nombre: ${cita.nombre_paciente}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Fecha: ${cita.fecha}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Hora: ${cita.hora}", style = MaterialTheme.typography.bodyLarge)
            //Text(text = "Asientos disponibles: ${viaje.asientosDisponibles}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
           // Button(onClick = {TomarViaje(viaje.id, viaje.asientosDisponibles )  }) {
            //    Text(text = "Tomar viaje")
            //}
        }
    }
}
*/
@Composable
fun muestracita(listacitas: MutableList<Cita>, idColeccion:String) {
    for (cita in listacitas) {

        Text(text = "\n${cita.fecha}\n${cita.hora}\n${cita.nombre_paciente}\n")
        Spacer(modifier = Modifier.padding(16.dp))

    }
}
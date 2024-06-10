package com.example.citaspedia

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.data.Cita
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.CitasPediaScreen
import com.example.citaspedia.ui.CitasPediaTopAppBar
import com.example.citaspedia.ui.CitaspediaTopAppBar
import com.example.citaspedia.ui.GameViewModel
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class MostrarCitaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                actualizarcita()
            }
        }
    }
}
@Composable
fun actualizarcita(navController: NavHostController = rememberNavController(), gameViewModel: GameViewModel =   viewModel(),
    // onNextButtonClicked: (Int) -> Unit,
                   modifier: Modifier = Modifier) {
    //val banderanumeros: Boolean=false
    val context = LocalContext.current
    val gameuiState by gameViewModel.uiState.collectAsState()
    val cita = remember { Cita() }
    var mostrarb by remember { mutableStateOf(true) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val backStackEntry by navController.currentBackStackEntryAsState()


    val currentScreen = CitasPediaScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasPediaScreen.Login.name
    )
    textocita(idc.value,cita)
    Scaffold(
        topBar = {
            CitasPediaTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
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
                value =cita.hora.value
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
                    onClick =  { (context as? ComponentActivity)?.finish() },
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
                        citaUpdate(idc.value,cita,context) },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Confirmar")
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

@Composable
fun textocita(id: String,cita:Cita){
    val db = Firebase.firestore
    LaunchedEffect(Unit) {
        val docRef = db.collection("citas").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    cita.nombre_paciente.value = document.getString("Nombre").orEmpty()
                    cita.fecha.value = document.getString("Fecha").orEmpty()
                    cita.hora.value = document.getString("Hora").orEmpty()
                } else {
                    //data = listOf("No such document")
                }
            }
            .addOnFailureListener {
                //data = "Error getting documents: ${exception.message}"
            }
    }
}

fun citaUpdate(id: String,cita: Cita, context: Context){
    val db = Firebase.firestore

    val updates = hashMapOf(
        "Nombre" to cita.nombre_paciente.value,
        "Fecha" to cita.fecha.value,
        "Hora" to cita.hora.value,

    )
    db.collection("citas").document(id)
        .set(updates, SetOptions.merge())
        .addOnSuccessListener {
            Toast.makeText(context,"Se actualizo correctamente", Toast.LENGTH_SHORT).show()

            Log.d("Firestore", "DocumentSnapshot successfully updated!")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error updating document", e)
            Toast.makeText(context, "Fallo al actualizar", Toast.LENGTH_SHORT).show()

        }
}
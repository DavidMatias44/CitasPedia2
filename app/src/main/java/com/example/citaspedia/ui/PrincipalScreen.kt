package com.example.citaspedia.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citaspedia.R
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pantalla_ini(
    PacienteButtonClicked: () -> Unit = {},
    ExpedienteButtonClicked: () -> Unit = {},
    RecetaButtonClicked: () -> Unit = {},
    CitaButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentDate = LocalDate.now()

    // Obtener la hora actual
    val currentTime = LocalTime.now()

    // Formatear la fecha
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = currentDate.format(dateFormatter)

    // Formatear la hora
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val formattedTime = currentTime.format(timeFormatter)

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
        var nombrePaciente by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(45.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("$formattedDate $formattedTime")

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nombrePaciente,
                onValueChange = { nombrePaciente = it }
            )
            Button(
                onClick = CitaButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Buscar paciente")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = PacienteButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Registrar un paciente")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = CitaButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Registrar una cita")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = ExpedienteButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Elaborar un expediente ")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = RecetaButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Realizar una receta")
            }
        }
    }
}




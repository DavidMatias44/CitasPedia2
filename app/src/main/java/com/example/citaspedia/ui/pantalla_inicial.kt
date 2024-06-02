package com.example.citaspedia.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citaspedia.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pantalla_ini(PacienteButtonClicked: () -> Unit = {},
                 ExpedienteButtonClicked: () -> Unit = {},
                 RecetaButtonClicked: () -> Unit = {},
                 CitaButtonClicked: () -> Unit = {},
                 modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            //CitaspediaTopAppBar()
            Text(
                text = stringResource(id = R.string.pantalla_principal),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                   // .padding(start = 56.dp)

            )
        }

    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            //val imageView = findViewById<ImageView>(R.id.imageView)
            Button(
                onClick = PacienteButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Registrar un paciente")
            }
            Spacer(modifier = Modifier.height(16.dp))
            //val imageView = findViewById<ImageView>(R.id.imageView)
            Button(
                onClick = CitaButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Registrar una cita")
            }
            Spacer(modifier = Modifier.height(16.dp))
            //val imageView = findViewById<ImageView>(R.id.imageView)
            Button(
                onClick = ExpedienteButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Elaborar un expediente ")
            }
            Spacer(modifier = Modifier.height(16.dp))
            //val imageView = findViewById<ImageView>(R.id.imageView)
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




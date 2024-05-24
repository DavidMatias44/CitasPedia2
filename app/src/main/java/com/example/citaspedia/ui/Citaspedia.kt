package com.example.citaspedia.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citaspedia.R
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.main_text
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button

@Composable
fun Citaspedia() {
    val paciente = remember { Paciente() }

    Scaffold(
        topBar = {
            CitaspediaTopAppBar()
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
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = R.string.nombre),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.nombre.value,
                onValueChange = { newValue ->
                    paciente.nombre.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Edad:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.edad.value,
                onValueChange = { newValue ->
                    paciente.edad.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Curp:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.curp.value,
                onValueChange = { newValue ->
                    paciente.curp.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Motivo de ingreso:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.motivoIngreso.value,
                onValueChange = { newValue ->
                    paciente.motivoIngreso.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Motivo de lesión:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.motivoLesion.value,
                onValueChange = { newValue ->
                    paciente.motivoLesion.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Peso:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.peso.value,
                onValueChange = { newValue ->
                    paciente.peso.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Temperatura:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.temperatura.value,
                onValueChange = { newValue ->
                    paciente.temperatura.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Talla:",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.talla.value,
                onValueChange = { newValue ->
                    paciente.talla.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(

            ) {
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = denied_button
                    )
                ) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = { pacienteInsert(paciente) },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Registrar")
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
        }
        /*
            TODO: Falta poner el cuadro de texto para Sexo.
            TODO: Poder insertar texto en los campos y guardarlos en variables.
            TODO: Poder navegar entre cuadros de texto sin tener que cerrar el teclado.
            TODO: Darle funcionalidad a los botones para que realicen acciones en la db.
            TODO: Crear las strings en strings.xml
         */
    }
}

const val TAG = "INSERT"
fun pacienteInsert(paciente: Paciente) {
    Log.d(TAG, paciente.toString())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaspediaTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_citaspedia),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = main_text,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewCitaspediaTopAppBar() {
   CitasPediaTheme {
       CitaspediaTopAppBar()
   }
}

@Preview
@Composable
fun PreviewCitaspedia() {
    CitasPediaTheme {
        Citaspedia()
    }
}

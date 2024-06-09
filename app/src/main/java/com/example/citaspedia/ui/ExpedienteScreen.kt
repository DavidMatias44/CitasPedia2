package com.example.citaspedia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citaspedia.R
import com.example.citaspedia.data.Expediente
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button


@Composable
fun ExpedienteScreen(
    gameViewModel: GameViewModel =   viewModel(),
    CancelarButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val gameuiState by gameViewModel.uiState.collectAsState()
    val expediente = remember { Expediente() }

    Scaffold(
        topBar = {
            Text(
                text = stringResource(id = R.string.expedientes),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth()
                .background(background_form)
        ) {
                Spacer(modifier = Modifier.padding(36.dp))
                Text(
                    text = stringResource(id = R.string.motivo_consulta),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.motivo_consulta.value,
                    onValueChange = { newValue ->
                        if(!contieneNumeros(newValue)) {
                            expediente.motivo_consulta.value = newValue
                        } else {
                            gameViewModel.banderanumeros=true
                            gameViewModel.help()
                        }
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError = gameuiState.error_nombre
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.exploracion_fi),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.exploracion_fisica.value,
                    onValueChange = { newValue ->
                        if (!contieneLetras(newValue)) {
                            expediente.exploracion_fisica.value = newValue
                        } else {
                            gameViewModel.banderaletras = true
                            gameViewModel.help_edad()
                        }
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError = gameuiState.error_edad
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.diagnostico),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.diagnostico.value,
                    onValueChange = { newValue ->
                        expediente.diagnostico.value = newValue
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError =gameuiState.error_responsable
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.tratamiento),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.tratamiento.value,
                    onValueChange = { newValue ->
                        expediente.tratamiento.value = newValue
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError =gameuiState.error_num_telefono
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.examenes_lab),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.examenes_laboratorio.value,
                    onValueChange = { newValue ->
                        expediente.examenes_laboratorio.value = newValue
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError = gameuiState.error_num_telefono
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.pronostico),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = expediente.pronostico.value,
                    onValueChange = { newValue ->
                        expediente.pronostico.value = newValue
                    },
                    modifier = Modifier
                        .padding(start = 32.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    isError =gameuiState.error_num_telefono
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                   modifier = Modifier.padding(start = 44.dp)
                ) {
                    Button(
                        onClick = CancelarButtonClicked,
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = denied_button
                        )
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Button(
                        onClick = {
                            expediente.create()
                        },
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
    }
}

@Preview
@Composable
fun ExpedienteScreenPreview(
    modifier: Modifier = Modifier
) {
    CitasPediaTheme {
        ExpedienteScreen()
    }
}
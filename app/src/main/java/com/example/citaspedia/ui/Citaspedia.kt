package com.example.citaspedia.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citaspedia.LoginScreen
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun Citaspedia(gameViewModel: GameViewModel = viewModel(), modifier: Modifier = Modifier) {
    val gameuiState by gameViewModel.uiState.collectAsState()
    val paciente = remember { Paciente() }

    Scaffold(
        /* topBar = {
            CitaspediaTopAppBar()
        }*/
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
                    if (!contieneNumeros(newValue)) {
                        paciente.nombre.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier.testTag("nombreTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )

            Text(
                text = stringResource(id = R.string.edad),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.edad.value,
                onValueChange = { newValue ->
                    if (!contieneLetras(newValue)) {
                        paciente.edad.value = newValue
                    } else {
                        gameViewModel.banderaletras = true
                        gameViewModel.help_edad()
                    }
                },
                modifier = Modifier.testTag("edadTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_edad
            )

            Text(
                text = stringResource(id = R.string.sexo),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            OutlinedTextField(
                value = paciente.sexo.value,
                onValueChange = { newValue ->
                    paciente.sexo.value = newValue
                },
                modifier = Modifier.testTag("sexoTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_sexo
            )

            Text(
                text = stringResource(id = R.string.curp),
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
                modifier = Modifier.testTag("curpTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_curp
            )

            Text(
                text = stringResource(id = R.string.motivoingreso),
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
                modifier = Modifier.testTag("moingTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_ingreso
            )

            Text(
                text = stringResource(id = R.string.motivolesion),
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
                modifier = Modifier.testTag("motlesTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_lesion
            )

            Text(
                text = stringResource(id = R.string.peso),
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
                modifier = Modifier.testTag("pesoTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_peso
            )

            Text(
                text = stringResource(id = R.string.temperatura),
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
                modifier = Modifier.testTag("tempTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_tempperatura
            )

            Text(
                text = stringResource(id = R.string.talla),
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
                modifier = Modifier.testTag("tallaTextField"),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_talla
            )

            Spacer(modifier = Modifier.padding(16.dp))
            Row {
                Button(
                    onClick = {},
                    modifier = Modifier.testTag("CancelarButton"),
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
                    modifier = Modifier.testTag("AceptarButton"),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Registrar")
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            /*
           TODO: Falta poner el cuadro de texto para Sexo. ya
           TODO: Poder insertar texto en los campos y guardarlos en variables.
           TODO: Poder navegar entre cuadros de texto sin tener que cerrar el teclado.
           TODO: Darle funcionalidad a los botones para que realicen acciones en la db.
           TODO: Crear las strings en strings.xml ya
        */
        }

        if (gameuiState.hayerrornum) {
            errornum()
        }
        if (gameuiState.hayerrorlet) {
            errorlet()
        }
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
//@Composable
fun contieneNumeros(cadena: String): Boolean {
    val regex = "\\d".toRegex()


    return regex.containsMatchIn(cadena)
}
fun contieneLetras(cadena: String): Boolean {
    val regex = "[a-zA-Z]".toRegex()
    return regex.containsMatchIn(cadena)

}

fun curp(cadena: String): Boolean {
    var tamano = cadena.length
    if(tamano!=18){
        return false
    }
    val parte_1 = cadena.substring(0,4)

    val regex ="\\d".toRegex()
    if(regex.containsMatchIn(parte_1)){
        return false
    }
    val parte_2 = cadena.substring(4,10)
    val regex2 = "[a-zA-Z]".toRegex()
    if(regex2.containsMatchIn(parte_2)){
        return false
    }
    val parte_3 = cadena.substring(10,16)
    val regex3 = "\\d".toRegex()
    if(regex3.containsMatchIn(parte_3)){
        return false
    }
    return true
}
@Composable
private fun errornum(
    gameViewModel: GameViewModel =   viewModel(),
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = stringResource(R.string.error1)) },
        // text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    gameViewModel.errornumeros()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {

        }
    )
}

@Composable
private fun errorlet(
    gameViewModel: GameViewModel =   viewModel(),
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = stringResource(R.string.error2)) },
        // text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    gameViewModel.errorletras()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {

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
/*
@Preview
@Composable
fun PreviewCitaspedia() {
    CitasPediaTheme {
        Citaspedia()
    }
}
*/
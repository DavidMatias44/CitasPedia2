package com.example.citaspedia.ui

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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.PacienteActivity
import com.example.citaspedia.R
import com.example.citaspedia.actualizar
import com.example.citaspedia.data.Expediente
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.idd
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.exp

class ActualizaExpedienteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                ActualizaExpediente()
            }
        }
    }
}

@Composable
fun ActualizaExpediente(
    gameViewModel: GameViewModel =   viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val gameuiState by gameViewModel.uiState.collectAsState()
    val expediente = remember { Expediente() }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()

    texto(expedienteId.value, expediente)
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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.paciente_nombre),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedTextField(
                value = expediente.paciente_nombre.value,
                onValueChange = { newValue ->
                    if (!contieneNumeros(newValue)) {
                        expediente.paciente_nombre.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.motivo_consulta.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.exploracion_fisica.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.diagnostico.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.tratamiento.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.examenes_laboratorio.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
                    if (!contieneNumeros(newValue)) {
                        expediente.pronostico.value = newValue
                    } else {
                        gameViewModel.banderanumeros = true
                        gameViewModel.help()
                    }
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = gameuiState.error_nombre
            )
            Spacer(modifier = Modifier.padding(12.dp))



            Spacer(modifier = Modifier.padding(16.dp))
            Row(

            ) {
                // var isLoading:Boolean=false

                Button(
                    onClick = { (context as? ComponentActivity)?.finish() },
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
                        isLoading=true
                        expedienteUpdate(expedienteId.value, expediente, context)
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Confirmar")
                }
                val mostrarCitas: () -> Unit = {
                    val intent = Intent(context, PacienteActivity::class.java)
                    context.startActivity(intent)
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = mostrarCitas ,
                    shape = RectangleShape,
                    enabled = isLoading,
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
    if(gameuiState.hayerrornum){
        errornum()
    }
    if(gameuiState.hayerrorlet){
        errorlet()
    }
}

@Composable
fun texto(
    id: String,
    expediente: Expediente
){
    val db = Firebase.firestore
    LaunchedEffect(Unit) {
        val docRef = db.collection("expedientes").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    expediente.paciente_nombre.value = document.getString("paciente_nombre").orEmpty()
                    expediente.motivo_consulta.value = document.getString("motivo_consulta").orEmpty()
                    expediente.exploracion_fisica.value = document.getString("exploracion_fisica").orEmpty()
                    expediente.diagnostico.value = document.getString("diagnostico").orEmpty()
                    expediente.tratamiento.value = document.getString("tratamiento").orEmpty()
                    expediente.examenes_laboratorio.value = document.getString("examenes_laboratorio").orEmpty()
                    expediente.pronostico.value = document.getString("pronostico").orEmpty()
                }
            }
            .addOnFailureListener {
            }
    }
}

fun expedienteUpdate(
    id: String,
    expediente: Expediente,
    context: Context
) {
    val db = Firebase.firestore
    val updates = hashMapOf(
        "paciente_nombre" to expediente.paciente_nombre.value,
        "motivo_consulta" to expediente.motivo_consulta.value,
        "exploracion_fisica" to expediente.exploracion_fisica.value,
        "diagnostico" to expediente.diagnostico.value,
        "tratamiento" to expediente.tratamiento.value,
        "examenes_laboratorio" to expediente.examenes_laboratorio.value,
        "pronostico" to expediente.pronostico.value
    )
    db.collection("expedientes").document(id)
        .set(updates, SetOptions.merge())
        .addOnSuccessListener {
            val texto = "Se actualizo correctamente"
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()

            Log.d("Firestore", "DocumentSnapshot successfully updated!")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error updating document", e)
        }
}
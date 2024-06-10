package com.example.citaspedia

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
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.CitasPediaScreen
import com.example.citaspedia.ui.CitasPediaTopAppBar
import com.example.citaspedia.ui.GameViewModel
import com.example.citaspedia.ui.contieneLetras
import com.example.citaspedia.ui.contieneNumeros
import com.example.citaspedia.ui.errorlet
import com.example.citaspedia.ui.errornum
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MostrarPacienteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                actualizar()
            }
        }
    }
}

@Composable
fun actualizar(gameViewModel: GameViewModel =   viewModel(),
               navController: NavHostController = rememberNavController(),
               modifier: Modifier = Modifier) {

    val gameuiState by gameViewModel.uiState.collectAsState()
    val paciente = remember { Paciente() }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()


    val currentScreen = CitasPediaScreen.valueOf(
        backStackEntry?.destination?.route ?: CitasPediaScreen.Login.name
    )
    texto(idd.value,paciente)
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
            // Spacer(modifier = Modifier.padding(36.dp))
            Text(
                text = stringResource(id = R.string.nombre),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)

            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(


                value =paciente.nombre.value,
                // },
                onValueChange = { newValue ->
                    if(!contieneNumeros(newValue)){ paciente.nombre.value= newValue}else{ gameViewModel.banderanumeros=true
                        gameViewModel.help()
                    }
                }

                ,
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
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = R.string.edad),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                //gameViewModel.banderanumeros=true,
                //  gameViewModel.help(),
                value = paciente.edad.value
                ,
                onValueChange = { newValue ->
                    if(!contieneLetras(newValue)){ paciente.edad.value = newValue}else{ gameViewModel.banderaletras=true
                        gameViewModel.help_edad()
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
                isError = gameuiState.error_edad
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = R.string.sexo),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            var H= false
            var M= false
            if(paciente.sexo.value=="Hombre"){
                 H= true
            }else if(paciente.sexo.value=="Mujer"){
                 M= true
            }

            val (checkedState, onStateChange) = remember { mutableStateOf(H) }
            val (checkedState2, onStateChange2) = remember { mutableStateOf(M) }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
                    .height(56.dp)
                    .toggleable(
                        value = checkedState,
                        onValueChange = {
                            onStateChange(!checkedState)

                            if (checkedState2) {
                                onStateChange2(false)
                            }
                        },
                        role = Role.Checkbox
                    )

                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = "Hombre",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
                    .height(56.dp)
                    .toggleable(
                        value = checkedState2,
                        onValueChange = {
                            onStateChange2(!checkedState2)
                            if (checkedState) {
                                onStateChange(false)
                            }
                        },
                        role = Role.Checkbox
                    )

                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState2,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = "Mujer",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))


            Text(
                text = stringResource(id = R.string.responsable),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = paciente.responsable.value,
                onValueChange = { newValue ->
                    paciente.responsable.value = newValue
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError =gameuiState.error_responsable
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = R.string.num_responsable),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = paciente.num_telefonico.value,
                onValueChange = { newValue ->
                    paciente.num_telefonico.value = newValue
                },
                modifier = Modifier
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError =gameuiState.error_num_telefono
            )



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
                    onClick = { isLoading=true
                        pacienteUpdate(idd.value,paciente,checkedState,checkedState2,context)
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
fun texto(id: String,paciente: Paciente){
    val db = Firebase.firestore
    LaunchedEffect(Unit) {
        val docRef = db.collection("pacientes").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    paciente.nombre.value = document.getString("Nombre").orEmpty()
                    paciente.edad.value = document.getString("Edad").orEmpty()
                    paciente.sexo.value = document.getString("Sexo").orEmpty()
                    paciente.responsable.value = document.getString("Responsable").orEmpty()
                    paciente.num_telefonico.value = document.getString("Numero").orEmpty()

                } else {
                    //data = listOf("No such document")
                }
            }
            .addOnFailureListener {
                //data = "Error getting documents: ${exception.message}"
            }
    }
}

fun pacienteUpdate(id: String,paciente: Paciente, checkedState:Boolean, checkedState2:Boolean,context: Context){

    val db = Firebase.firestore
    if(checkedState){
        paciente.sexo.value="Hombre"
    }else if (checkedState2){
       paciente.sexo.value="Mujer"
    }
    val updates = hashMapOf(
        "Nombre" to paciente.nombre.value,
        "Edad" to paciente.edad.value,
        "Sexo" to paciente.sexo.value,
        "Responsable" to paciente.responsable.value,
        "Numero" to paciente.num_telefonico.value
    )
    db.collection("pacientes").document(id)
        .set(updates, SetOptions.merge())
        .addOnSuccessListener {
            Toast.makeText(context, "Se actualizo correctamente", Toast.LENGTH_SHORT).show()

            Log.d("Firestore", "DocumentSnapshot successfully updated!")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error updating document", e)
            Toast.makeText(context, "Fallo al actualizar", Toast.LENGTH_SHORT).show()

        }
}
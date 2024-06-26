package com.example.citaspedia.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.citaspedia.PacienteActivity
import com.example.citaspedia.idd
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



@SuppressLint("UnrememberedMutableState")
@Composable
fun Citaspedia(gameViewModel: GameViewModel =   viewModel(),
               navController: NavHostController = rememberNavController(),
               modifier: Modifier = Modifier,
               MostrarButtonClicked: () -> Unit = {},
               CancelarButtonClicked: () -> Unit = {}) {
    //val banderanumeros: Boolean=false
    val gameuiState by gameViewModel.uiState.collectAsState()
    val paciente = remember { Paciente() }
    var isLoading by remember { mutableStateOf(false) }
    var idPaciente by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            //CitaspediaTopAppBar()
            Text(
                text = stringResource(id = R.string.pacientes),
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


                value = //if (contieneNumeros(paciente.nombre.value)) {
                //   ""
                // } else{
                paciente.nombre.value,
                // },
                onValueChange = { newValue ->
                    if(!contieneNumeros(newValue)){ paciente.nombre.value = newValue}else{ gameViewModel.banderanumeros=true
                        gameViewModel.help()
                    }
                }

                ,
                modifier = Modifier
                    .height(45.dp).testTag("nombreTextField"),
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
                value =paciente.edad.value
                ,
                onValueChange = { newValue ->
                    if(!contieneLetras(newValue)){ paciente.edad.value = newValue}else{ gameViewModel.banderaletras=true
                        gameViewModel.help_edad()
                    }

                },
                modifier = Modifier
                    .height(45.dp).testTag("EdadTextField"),
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
            val H: Boolean= false
            val M: Boolean= false
            val (checkedState, onStateChange) = remember { mutableStateOf(H) }
            val (checkedState2, onStateChange2) = remember { mutableStateOf(M) }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp)
                    .height(56.dp)
                    .toggleable(
                        value = checkedState,
                        onValueChange = { onStateChange(!checkedState)

                            if(checkedState2){
                                onStateChange2(false)
                            }},
                        role = Role.Checkbox
                    )

                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null, // null recommended for accessibility with screenreaders
                    modifier = Modifier.testTag("HombreCheckbox")
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
                        onValueChange = { onStateChange2(!checkedState2)
                            if(checkedState){
                                onStateChange(false)
                            }},
                        role = Role.Checkbox
                    )

                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState2,
                    onCheckedChange = null, // null recommended for accessibility with screenreaders
                    modifier = Modifier.testTag("MujerCheckbox")
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
                onValueChange ={ newValue ->
                    if(!contieneNumeros(newValue)){ paciente.responsable.value = newValue}else{ gameViewModel.banderanumeros=true
                        gameViewModel.help_responsable()
                    }
                },
                modifier = Modifier
                    .height(45.dp).testTag("Responsabletest"),
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
                    if (!contieneLetras(newValue)) {
                        paciente.num_telefonico.value = newValue


                    } else {
                        gameViewModel.banderaletras = true
                        gameViewModel.help_num_telefonico()
                    }
                },
                modifier = Modifier
                    .height(45.dp).testTag("Telefonotest"),
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
                    onClick = { isLoading=true
                        pacienteInsert(paciente,checkedState,checkedState2,context)
                    },
                    modifier = Modifier
                        .testTag("RegistrarB"),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Registrar")
                }
                val mostrarunPaciente: () -> Unit = {
                    val intent = Intent(context, PacienteActivity::class.java)
                    context.startActivity(intent)
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = mostrarunPaciente ,
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
        /*
            TODO: Falta poner el cuadro de texto para Sexo. ya
            TODO: Poder insertar texto en los campos y guardarlos en variables.
            TODO: Poder navegar entre cuadros de texto sin tener que cerrar el teclado.
            TODO: Darle funcionalidad a los botones para que realicen acciones en la db.
            TODO: Crear las strings en strings.xml ya
         */
    }
    if(gameuiState.hayerrornum){
        errornum()
    }
    if(gameuiState.hayerrorlet){
        errorlet()
    }
}

const val TAG = "INSERT"


fun pacienteInsert(
    paciente: Paciente, checkedState:Boolean, checkedState2:Boolean, context: Context
) {

    //Log.d(TAG, paciente.nombre.toString())
    // Create a new user with a first and last name
    val db = Firebase.firestore

    if (checkedState) {
        paciente.sexo.value = "Hombre"
    } else if (checkedState2) {
        paciente.sexo.value = "Mujer"
    }
    val unpaciente = hashMapOf(
        "Nombre" to paciente.nombre.value,
        "Edad" to paciente.edad.value,
        "Sexo" to paciente.sexo.value,
        "Responsable" to paciente.responsable.value,
        "Numero" to paciente.num_telefonico.value
    )
    if (paciente.num_telefonico.value.length == 10){
// Add a new document with a generated I
        db.collection("pacientes")
            .add(unpaciente)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(context, "Se registro correctamente", Toast.LENGTH_SHORT).show()

            }

            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(context, "Fallo al registrar", Toast.LENGTH_SHORT).show()

            }

    // Log.d(TAG,"sss ${id.id}")
}else{
        Toast.makeText(context, "El número telefónico debe ser de 10 dígitos", Toast.LENGTH_SHORT).show()
        //gameuiState.error_num_telefono
    }

}


//@SuppressLint("SuspiciousIndentation")

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
fun errornum(
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
fun errorlet(
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
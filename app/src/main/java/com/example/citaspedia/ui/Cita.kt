package com.example.citaspedia.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citaspedia.R
import com.example.citaspedia.data.Cita
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Citas(gameViewModel: GameViewModel =   viewModel(),
    // onNextButtonClicked: (Int) -> Unit,
                modifier: Modifier = Modifier,
                CancelarButtonClicked: () -> Unit = {}
) {
    //val banderanumeros: Boolean=false
    val gameuiState by gameViewModel.uiState.collectAsState()
    val cita = remember { Cita() }

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


                value = cita.fecha.value,

                onValueChange = { newValue ->
                    if(!contieneLetras(newValue)){cita.fecha.value = newValue}else{ gameViewModel.banderaletras=true
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
                value =cita.hora.value
                ,
                onValueChange = { newValue ->
                    if(!contieneLetras(newValue)){ cita.hora.value = newValue}else{ gameViewModel.banderaletras=true
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
                    onClick = { citaInsert(cita) },
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

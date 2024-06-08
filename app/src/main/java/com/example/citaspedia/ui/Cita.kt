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
import com.example.citaspedia.ui.theme.approve_button
import com.example.citaspedia.ui.theme.background_form
import com.example.citaspedia.ui.theme.denied_button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Citas(gameViewModel: GameViewModel =   viewModel(),
    // onNextButtonClicked: (Int) -> Unit,
                modifier: Modifier = Modifier,
                CancelarButtonClicked: () -> Unit = {},
          MostrarButtonClicked: () -> Unit = {},
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
                    .height(50.dp),
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
                    .height(50.dp),
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
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = MostrarButtonClicked ,
                    shape = RectangleShape,
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

@Composable
fun mostrar(modifier: Modifier = Modifier) {


    /* Important: It is not a good practice to access data source directly from the UI.
        In later units you will learn how to use ViewModel in such scenarios that takes the
        data source as a dependency and exposes heroes.
         */
    val db = Firebase.firestore
    var idColeccion:String=""
    //val listaPersonas = PacienteRepo.pacientes
    db.collection("citas")//.document("meq4CKpLUagt7z9aHFbO")
        .get()
        .addOnSuccessListener { result ->
            // if (document != null && document.exists()) {
            // Acceder a cada campo
            for (document in result) {
                val cita= Cita()
                idColeccion = document.id
                cita.fecha.value = document.getString("Fecha").toString()

                cita.hora.value =
                    document.getString("Hora").toString() // Convertir a Int si es necesario

                cita.nombre_paciente.value = document.getString("Nombre").toString()


                CitasRepo.citas.add(cita)

            }

            //PacientesList(pacientes = listaPersonas)  // Mostrar los valores
            //for(paciente in listaPersonas){
            for (cita in CitasRepo.citas) {
                Log.w("Listadecita", cita.fecha.value)//}
                Log.w("Listadecita", cita.hora.value)
                Log.w("Listadecita", cita.nombre_paciente.value)
                //} else {
                //   println("No se encontró el documento")
            }

            //} else {
            //   println("No se encontró el documento")
            //   }
        }
        .addOnFailureListener { exception ->
            println("Error al obtener el documento: $exception")
        }


    val listacitas =
       CitasRepo.citas
    muestracita(listacitas,idColeccion)

    //PacientesList(pacientes = listapacientes)
    /* for(pacient in PacienteRepo.pacientes) {
        PacientesItem(paciente = pacient,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                // Animate each list item to slide in vertically
               )
    }
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
    LazyColumn() {
        itemsIndexed(PacienteRepo.pacientes) { index, paciente ->
            PacientesItem(
                paciente = paciente,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    //Animate each list item to slide in vertically
                    .animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioLowBouncy
                            ),
                            initialOffsetY = { it * (index + 1) } // staggered entrance
                        )
                    )
            )
        }
    }
}*/



}

@Composable
fun muestracita(listacitas: MutableList<Cita>, idColeccion:String) {
    for (cita in listacitas) {

        Text(text = "\n${cita.fecha.value}\n${cita.hora.value}\n${cita.nombre_paciente.value}\n")
        Spacer(modifier = Modifier.padding(16.dp))

    }
}
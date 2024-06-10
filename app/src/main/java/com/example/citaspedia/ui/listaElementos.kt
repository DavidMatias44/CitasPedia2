package com.example.citaspedia.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.citaspedia.data.Paciente
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InterfazList(modifier: Modifier = Modifier) {


    /* Important: It is not a good practice to access data source directly from the UI.
        In later units you will learn how to use ViewModel in such scenarios that takes the
        data source as a dependency and exposes heroes.
         */
    val db = Firebase.firestore

    //val listaPersonas = PacienteRepo.pacientes
    db.collection("pacientes")//.document("meq4CKpLUagt7z9aHFbO")
        .get()
        .addOnSuccessListener { result ->
            // if (document != null && document.exists()) {
            // Acceder a cada campo
            for (document in result) {
                val pacien = Paciente()
                val resulData = document.data

                pacien.nombre = mutableStateOf(document.getString("Nombre").orEmpty())

                pacien.edad =mutableStateOf( document.getString("Edad").toString()) // Convertir a Int si es necesario

                pacien.sexo= mutableStateOf(document.getString("Sexo").toString())

                pacien.responsable = mutableStateOf(document.getString("Responsable").toString())

                pacien.num_telefonico =mutableStateOf( document.getString("Numero").toString())


                PacienteRepo.pacientes.add(pacien)

            }

            //PacientesList(pacientes = listaPersonas)  // Mostrar los valores
            //for(paciente in listaPersonas){


            //} else {
            //   println("No se encontró el documento")
            //   }
        }
        .addOnFailureListener { exception ->
            println("Error al obtener el documento: $exception")
        }


   val listapacientes =
       PacienteRepo.pacientes


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


fun obtienepaciente(){

}
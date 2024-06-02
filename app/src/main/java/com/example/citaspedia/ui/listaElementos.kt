package com.example.citaspedia.ui

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.citaspedia.data.Paciente
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

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

                pacien.nombre.value = document.getString("Nombre").toString()

                pacien.edad.value =
                    document.getString("Edad").toString() // Convertir a Int si es necesario

                pacien.sexo.value = document.getString("Sexo").toString()

                pacien.responsable.value = document.getString("Responsable").toString()

                pacien.num_telefonico.value = document.getString("Numero").toString()


                PacienteRepo.pacientes.add(pacien)

            }

            //PacientesList(pacientes = listaPersonas)  // Mostrar los valores
            //for(paciente in listaPersonas){
            for (paciente in PacienteRepo.pacientes) {
                Log.w("Listaderepo", paciente.nombre.value)//}
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


    val listapacientes = PacienteRepo.pacientes
    /* for(pacient in PacienteRepo.pacientes) {
        PacientesItem(paciente = pacient,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                // Animate each list item to slide in vertically
               )
    }*/
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
}



    }


fun obtienepaciente(){

}
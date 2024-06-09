package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

data class Paciente(
    var nombre: MutableState<String> = mutableStateOf(""),
    var edad: MutableState<String> = mutableStateOf(""),
    var sexo: MutableState<String> = mutableStateOf(""),
    var responsable: MutableState<String> = mutableStateOf(""),
    var num_telefonico: MutableState<String> = mutableStateOf(""),
) {
    fun get(nombre: String) {
        val database = Firebase.firestore
        val pacientesRef = database.collection("pacientes").document(nombre)

        pacientesRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val patientData = document.toObject<Paciente>()
                    println("si esta")
                } else {
                    println("no esta")
                }
            }
            .addOnFailureListener { exception ->
            }
    }
}
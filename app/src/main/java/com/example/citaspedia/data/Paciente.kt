package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

data class Paciente(
    var id: MutableState<String> =  mutableStateOf("") ,
    var nombre: MutableState<String> = mutableStateOf(""),
    var edad: MutableState<String> = mutableStateOf(""),
    var sexo: MutableState<String> = mutableStateOf(""),
    var responsable: MutableState<String> = mutableStateOf(""),
    var num_telefonico: MutableState<String> = mutableStateOf(""),
) {
    fun get(callback: (List<Paciente>) -> Unit) {
        val database = Firebase.firestore
        val pacientesRef = database.collection("pacientes")

        pacientesRef.get()
            .addOnSuccessListener { documents ->
                val pacientesList = mutableListOf<Paciente>()
                for (document in documents) {
                    val nombre = mutableStateOf(document.getString("nombre") ?: "")
                    val edad = mutableStateOf(document.getString("edad") ?: "")
                    val sexo = mutableStateOf(document.getString("sexo") ?: "")
                    val responsable = mutableStateOf(document.getString("responsable") ?: "")
                    val numTelefonico = mutableStateOf(document.getString("num_telefonico") ?: "")

                    val paciente = Paciente(nombre, edad, sexo, responsable, numTelefonico)
                    pacientesList.add(paciente)
                }
                println("si se pudo bro")
                callback(pacientesList)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
                callback(emptyList())
            }
    }
}
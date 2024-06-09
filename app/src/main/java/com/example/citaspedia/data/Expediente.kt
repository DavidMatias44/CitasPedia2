package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class Expediente(
    var paciente_nombre: MutableState<String> = mutableStateOf(""),
    var motivo_consulta: MutableState<String> = mutableStateOf(""),
    var exploracion_fisica: MutableState<String> = mutableStateOf(""),
    var diagnostico: MutableState<String> = mutableStateOf(""),
    var tratamiento: MutableState<String> = mutableStateOf(""),
    var examenes_laboratorio: MutableState<String> = mutableStateOf(""),
    var pronostico: MutableState<String> = mutableStateOf("")
) {
    fun create() {
        val database = Firebase.firestore

        val expedienteToInsert = hashMapOf(
            "paciente_nombre" to this.paciente_nombre,
            "motivo_consulta" to this.motivo_consulta,
            "exploracion_fisica" to this.exploracion_fisica,
            "diagnostico" to this.diagnostico,
            "tratamiento" to this.tratamiento,
            "examenes_laboratorio" to this.examenes_laboratorio,
            "pronostico" to this.pronostico
        )

        database.collection("expedientes").add(expedienteToInsert)
            .addOnSuccessListener { documentReference ->
                println("Expediente insertado correctamente.")
            }
            .addOnFailureListener {
                println("Error insertando expediente.")
            }
    }
}
package com.example.citaspedia.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class Expediente(
    var id: MutableState<String> = mutableStateOf(""),
    var paciente_nombre: MutableState<String> = mutableStateOf(""),
    var motivo_consulta: MutableState<String> = mutableStateOf(""),
    var exploracion_fisica: MutableState<String> = mutableStateOf(""),
    var diagnostico: MutableState<String> = mutableStateOf(""),
    var tratamiento: MutableState<String> = mutableStateOf(""),
    var examenes_laboratorio: MutableState<String> = mutableStateOf(""),
    var pronostico: MutableState<String> = mutableStateOf("")
) {
    fun create(context: Context) {
        val database = Firebase.firestore

        Log.d("EXP", "$this")

        val expedienteToInsert = hashMapOf(
            "paciente_nombre" to this.paciente_nombre.value,
            "motivo_consulta" to this.motivo_consulta.value,
            "exploracion_fisica" to this.exploracion_fisica.value,
            "diagnostico" to this.diagnostico.value,
            "tratamiento" to this.tratamiento.value,
            "examenes_laboratorio" to this.examenes_laboratorio.value,
            "pronostico" to this.pronostico.value
        )
        Log.d("EXP", "$expedienteToInsert")

        database.collection("expedientes").add(expedienteToInsert)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Expediente insertado correctamente", Toast.LENGTH_SHORT).show()
                println("Expediente insertado correctamente.")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al insertar el expediente. ", Toast.LENGTH_SHORT).show()
                println("Error insertando expediente.")
            }
    }
}
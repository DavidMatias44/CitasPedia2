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

/*
fun pacienteInsert(paciente: Paciente, checkedState:Boolean, checkedState2:Boolean) {
    //Log.d(TAG, paciente.nombre.toString())
    // Create a new user with a first and last name
    val db = Firebase.firestore

    if(checkedState){
        paciente.sexo.value="Hombre"
    }else if (checkedState2){
        paciente.sexo.value="Mujer"
    }
    val unpaciente = hashMapOf(
        "Nombre" to paciente.nombre.value,
        "Edad" to paciente.edad.value,
        "Sexo" to paciente.sexo.value,
        "Responsable" to paciente.responsable.value,
        "Numero" to paciente.num_telefonico.value
    )

// Add a new document with a generated ID
    var id:String=""
    db.collection("pacientes")
        .add(unpaciente)
        .addOnSuccessListener { documentReference ->
            // Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            id=documentReference.id
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }

    // Log.d(TAG,"sss ${id.id}")

}

 */
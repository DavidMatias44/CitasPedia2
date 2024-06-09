package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Paciente(
    var nombre: MutableState<String> = mutableStateOf(""),
    var edad: MutableState<String> = mutableStateOf(""),
    var sexo: MutableState<String> = mutableStateOf(""),
    var responsable: MutableState<String> = mutableStateOf(""),
    var num_telefonico: MutableState<String> = mutableStateOf(""),
) {
    fun get(nombre: String) {

    }
}
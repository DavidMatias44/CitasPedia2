package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Paciente(
    var nombre: MutableState<String> = mutableStateOf(""),
    var edad: MutableState<String> = mutableStateOf(""),
    var sexo: MutableState<String> = mutableStateOf(""),
    //var curp: MutableState<String> = mutableStateOf(""),
   // var motivoIngreso: MutableState<String> = mutableStateOf(""),
    //var motivoLesion: MutableState<String> = mutableStateOf(""),
    //var peso: MutableState<String> = mutableStateOf(""),
    //var temperatura: MutableState<String> = mutableStateOf(""),
    //var talla: MutableState<String> = mutableStateOf("")
    var responsable: MutableState<String> = mutableStateOf(""),
    var num_telefonico: MutableState<String> = mutableStateOf(""),
) {

}
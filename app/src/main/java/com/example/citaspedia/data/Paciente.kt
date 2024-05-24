package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

data class Paciente(
    var nombre: MutableState<String> = mutableStateOf(""),
    var edad: MutableState<String> = mutableStateOf(""),
    var sexo: MutableState<String> = mutableStateOf(""),
    var curp: MutableState<String> = mutableStateOf(""),
    var motivoIngreso: MutableState<String> = mutableStateOf(""),
    var motivoLesion: MutableState<String> = mutableStateOf(""),
    var peso: MutableState<String> = mutableStateOf(""),
    var temperatura: MutableState<String> = mutableStateOf(""),
    var talla: MutableState<String> = mutableStateOf("")
) {

}
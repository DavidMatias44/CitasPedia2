package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Cita (var fecha: MutableState<String> = mutableStateOf(""),
                 var hora: MutableState<String> = mutableStateOf(""),
                 var nombre_paciente: MutableState<String> = mutableStateOf(""),){
}
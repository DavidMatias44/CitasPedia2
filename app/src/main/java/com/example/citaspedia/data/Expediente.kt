package com.example.citaspedia.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Expediente (var motivo_consulta: MutableState<String> = mutableStateOf(""),
                       var exploracion_fisica: MutableState<String> = mutableStateOf(""),
                       var diagnostico: MutableState<String> = mutableStateOf(""),
                       var tratamiento: MutableState<String> = mutableStateOf(""),
                       var examenes_laboratorio: MutableState<String> = mutableStateOf(""),
                       var pronostico: MutableState<String> = mutableStateOf(""),) {

}
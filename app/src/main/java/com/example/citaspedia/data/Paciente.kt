package com.example.citaspedia.data

data class Paciente(
    val nombre: String,
    val edad: Int,
    val sexo: String,
    val curp: String,
    val motivoIngreso: String,
    val motivoLesion: String?,
    val peso: Double,
    val temperatura: Double,
    val talla: Int
) {

}
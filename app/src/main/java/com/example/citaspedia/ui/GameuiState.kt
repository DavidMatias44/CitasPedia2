package com.example.citaspedia.ui

data class GameuiState(
    val hayerrornum: Boolean =false,
    val hayerrorlet: Boolean =false,
    val error_nombre:Boolean = false,
    val error_edad:Boolean = false,
    val error_sexo:Boolean = false,
    val error_curp:Boolean = false,
    val error_ingreso:Boolean = false,
    val error_lesion:Boolean = false,
    val error_peso:Boolean = false,
    val error_tempperatura:Boolean = false,
    val error_talla:Boolean = false,
) {

}
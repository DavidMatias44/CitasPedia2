package com.example.citaspedia.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameuiState())
    val uiState: StateFlow<GameuiState> = _uiState.asStateFlow()
    var banderanumeros: Boolean = false
    var banderaletras: Boolean = false

    var userGuess by mutableStateOf("")
        private set

    fun help(){
        if(banderanumeros) {
            _uiState.update { currentState ->
                currentState.copy(hayerrornum = true)
            }
            _uiState.update { currentState ->
                currentState.copy(error_nombre = true)
            }
          //  banderanumeros=false
        }
    }

    fun errornumeros(){
        banderanumeros=false
        _uiState.update { currentState ->
                currentState.copy(hayerrornum = false) }
        _uiState.update { currentState ->
            currentState.copy(error_nombre = false)
        }

    }
    fun help_edad(){
        if(banderaletras) {
            _uiState.update { currentState ->
                currentState.copy(hayerrorlet = true)
            }
            _uiState.update { currentState ->
                currentState.copy(error_edad = true)
            }
            //  banderanumeros=false
        }
    }
    fun errorletras(){
        banderaletras=false
        _uiState.update { currentState ->
            currentState.copy(hayerrorlet = false) }
        _uiState.update { currentState ->
            currentState.copy(error_edad = false)
        }

    }




}
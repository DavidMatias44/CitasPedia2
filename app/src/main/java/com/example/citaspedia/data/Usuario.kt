package com.example.citaspedia.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Usuario(
    var nombre: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf(""),
) {
    fun login(context: Context, onNextButtonClicked: () -> Unit) {
        var firebaseAuth = Firebase.auth
        this.nombre.value = ""

        firebaseAuth.signInWithEmailAndPassword(this.email.value, this.password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("inicia sesion")
                    Toast.makeText(context, "Bienvenido usuario: ${this.email.value}", Toast.LENGTH_SHORT).show()
                    onNextButtonClicked()
                } else {
                    println(" no inicia sesion")
                    Toast.makeText(context, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
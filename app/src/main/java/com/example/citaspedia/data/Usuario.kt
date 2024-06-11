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
    fun create(context: Context) {
        val firebaseAuth = Firebase.auth

        firebaseAuth.createUserWithEmailAndPassword(this.email.value, this.password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Usuario creado correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    task.exception?.let { exception ->
                        Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                    } ?: run {
                        Toast.makeText(context, "Error desconocido al crear al usuario.", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    fun login(context: Context, onNextButtonClicked: () -> Unit) {
        val firebaseAuth = Firebase.auth
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
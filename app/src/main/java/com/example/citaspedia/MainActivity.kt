package com.example.citaspedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.example.citaspedia.ui.CitasPediaApp

class MainActivity : ComponentActivity() {
    private val dataBase = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                CitasPediaApp()
            }
        }
    }
}



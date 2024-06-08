package com.example.citaspedia

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import com.google.firebase.analytics.FirebaseAnalytics

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.citaspedia.ui.theme.CitasPediaTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.example.citaspedia.ui.Citaspedia
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.res.painterResource
import com.example.citaspedia.ui.Pantalla_ini
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class MainActivity : ComponentActivity() {
    private val dataBase = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasPediaTheme {
                // A surface container using the 'background' color from the theme
               /* Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {*/
                   //Citaspedia()
                   CitasApp()
                //Pantalla_ini()
                //}
            }
        }
        /*Firebase.analytics.logEvent("app_open") {
            param("message", "Hello, Firebase!")
        }*/
    }
}



package com.example.citaspedia.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
//import androidx.compose.material3.Text
//import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.citaspedia.ui.theme.main_text
import com.example.citaspedia.data.Paciente
//import com.example.citaspedia.ui.pacienteRead
import com.google.firebase.firestore.ktx.firestore
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.citaspedia.R
import com.example.citaspedia.data.Usuario
import com.example.citaspedia.ui.theme.background_form
import kotlinx.coroutines.CoroutineScope

//usuario: user@gmail.com contraseña: 123456
@Composable
fun LoginScreen(
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var usuario = remember { Usuario() }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var isRegistrationProcessActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.inicio_sesion),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
        )
        TextField(
            value = usuario.email.value,
            onValueChange = { usuario.email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("emailTextField")
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = usuario.password.value,
            onValueChange = { usuario.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("passwordTextField")
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                usuario.login(context, onNextButtonClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("loginButton"),
            enabled = !isLoading
        ){
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        ClickableText(
            text = AnnotatedString("Crear una cuenta."),
            style = TextStyle(textDecoration = TextDecoration.Underline),
            onClick = {
                isRegistrationProcessActive = true
            }
        )
    }
    
    if (isRegistrationProcessActive) {
        RegistrationAlertDialog(
            usuario = usuario,
            onDissmissRequest = {
                isRegistrationProcessActive = false
                usuario.email.value = ""
                usuario.password.value = ""
            },
            onAcceptClicked = {
                isRegistrationProcessActive = false
            },
            context = context
        )
    }
}

@Composable
fun RegistrationAlertDialog(
    usuario: Usuario,
    onDissmissRequest: () -> Unit,
    onAcceptClicked: () -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = {
            onDissmissRequest()
        },
        content = {
            Card(
                modifier = Modifier.size(290.dp, 350.dp)
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(background_form)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Registro de usuario.",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Email: "
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = usuario.email.value,
                        onValueChange = {
                            usuario.email.value = it
                        }
                    )
                    Text(
                        text = "Contraseña: "
                    )
                    TextField(
                        value = usuario.password.value,
                        onValueChange = {
                            usuario.password.value = it
                        },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Row {
                        Button(
                            onClick = {
                                onDissmissRequest()
                            }
                        ) {
                            Text(text = "Cancelar")
                        }
                        Button(
                            onClick = {
                                usuario.create(context = context)
                                onAcceptClicked()
                            }
                        ) {
                            Text(text = "Aceptar")
                        }
                    }
                }

            }
        }
    )
}


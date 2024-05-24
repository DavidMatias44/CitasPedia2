package com.example.citaspedia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citaspedia.R
import com.example.citaspedia.ui.theme.CitasPediaTheme

@Composable
fun Citaspedia() {
    Scaffold(
        topBar = {
            CitaspediaTopAppBar()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(it).fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.nombre),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(start = 56.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )

            Text(
                text = "Edad:",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(start = 56.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                isError = false
            )
        }
        /*
            TODO: definir los campos de texto y los botones para el formulario
         */
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaspediaTopAppBar(
    modifier: Modifier = Modifier
) {
    /*
        TODO: definir colores para la barra superior.
        TODO: definir tal vez un logo o algo así.
     */
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}

@Preview
@Composable
fun PreviewCitaspediaTopAppBar() {
   CitasPediaTheme {
       CitaspediaTopAppBar()
   }
}

@Preview
@Composable
fun PreviewCitaspedia() {
    CitasPediaTheme {
        Citaspedia()
    }
}

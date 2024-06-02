package com.example.citaspedia.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citaspedia.data.Paciente
import com.example.citaspedia.data.Pacientemuestra
import com.example.citaspedia.ui.theme.approve_button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PacientesList(
    pacientes: List<Paciente>,
    modifier: Modifier = Modifier,
  //  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn() {
            itemsIndexed(pacientes) { index, paciente ->
                PacientesItem(
                    paciente = paciente,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        // Animate each list item to slide in vertically
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun PacientesItem(
   paciente: Paciente,
    modifier: Modifier = Modifier,
    UpdateButtonClicked: () -> Unit = {},
    DeleteButtonClicked: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text =paciente.nombre.value,
                    style = MaterialTheme.typography.displaySmall
                )
              /*  Spacer(Modifier.width(16.dp))
                Text(
                    text = "Edad: ${paciente.edad.value}",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Sexo: ${paciente.sexo.value}",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Responsable: ${paciente.responsable.value}",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Número: ${paciente.num_telefonico.value}",
                    style = MaterialTheme.typography.displaySmall
                )*/
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick =  { pacienteUpdate() } ,
                    shape = RectangleShape,
                    //enabled = isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Actualizar")
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = { pacienteDelete() } ,
                    shape = RectangleShape,
                  //  enabled = isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approve_button
                    )
                ) {
                    Text("Eliminar")
                }
            }
           /* Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))

            ) {
                Image(
                    painter = painterResource(paciente.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )*/
            }
        }
    }


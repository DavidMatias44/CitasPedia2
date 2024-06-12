package com.example.citaspedia

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.example.citaspedia.ui.Citas
import com.example.citaspedia.ui.Citaspedia
import com.example.citaspedia.ui.ExpedienteScreen
import com.example.citaspedia.ui.Pantalla_ini
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith
import com.example.citaspedia.ui.LoginScreen
import com.example.citaspedia.ui.MostrarExpedientes
import com.example.citaspedia.ui.MostrarExpedientesActivity
import java.security.Principal

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLogin() {

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(onNextButtonClicked = {})
            }
        }

        // Input email
        composeTestRule.onNodeWithTag("emailTextField")
            .performTextInput("user@gmail.com")

        // Input incorrect password
        composeTestRule.onNodeWithTag("passwordTextField")
            .performTextInput("123456")

        composeTestRule.onNodeWithTag("loginButton")
            .performClick()
        Thread.sleep(5000)
    }

}
class pacineteTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCitaspediaForm() {
        composeTestRule.setContent {
            MaterialTheme {
                Citaspedia()
            }
        }
        //Nombre
        composeTestRule.onNodeWithTag("nombreTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Carlos Quispe")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("El nombre no debe llevar números:")
            .assertDoesNotExist()
        //Edad
        composeTestRule.onNodeWithTag("EdadTextField")
            .assertExists("El campo de texto para la edad no existe.")
            .performTextInput("40")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("El nombre no debe llevar letras:")
            .assertDoesNotExist()
        //Sexo?
        /* composeTestRule.onNodeWithTag("HombreCheckbox")
             .assertExists("El checkbox para Hombre no existe.")
             .performClick()
         composeTestRule.waitForIdle()

         // Asegurarse de que se ha seleccionado el checkbox de Hombre
         composeTestRule.onNodeWithTag("HombreCheckbox")
             .assertIsOn()

         // Interactuar con el checkbox de Mujer
         composeTestRule.onNodeWithTag("MujerCheckbox")
             .assertExists("El checkbox para Mujer no existe.")
             .performClick()
         composeTestRule.waitForIdle()

         // Asegurarse de que se ha seleccionado el checkbox de Mujer
         composeTestRule.onNodeWithTag("MujerCheckbox")
             .assertIsOn()

         // Asegurarse de que el checkbox de Hombre está desactivado
         composeTestRule.onNodeWithTag("HombreCheckbox")
             .assertIsOff()*/
        //Responsable
        composeTestRule.onNodeWithTag("Responsabletest")
            .assertExists("El campo de texto para la responsable no existe.")
            .performTextInput("Arturo, su padre")
        composeTestRule.waitForIdle()
        //telefono
        composeTestRule.onNodeWithTag("Telefonotest")
            .assertExists("El campo de texto para la responsable no existe.")
            .performTextInput("1234567890")
        composeTestRule.waitForIdle()
        //Aceptar
        composeTestRule.onNodeWithTag("RegistrarB")
            .performClick()
        Thread.sleep(5000)
    }
    @Test
    fun testbusqueda() {
        composeTestRule.setContent {
            MaterialTheme {
                Pantalla_ini()
            }
        }
        composeTestRule.onNodeWithTag("Buscarcampo")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Carlos Quispe")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("BuscarButton")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(10000)
    }

    @Test
    fun MuestraPacientes(){
        composeTestRule.setContent {
            MaterialTheme {
                mostrarP()
            }
        }
        Thread.sleep(10000)

    }
    @Test
    fun testEliminarPaciente() {

        composeTestRule.setContent {
            MaterialTheme {
                mostrarP()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("EliminaGYSGTYNjxmtqiCpoY2j4")
            .performClick()
        composeTestRule.waitForIdle()

    }
    @Test
    fun testEditarPaciente() {
        composeTestRule.setContent {
            MaterialTheme {
                mostrarP()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("ActualizarGYSGTYNjxmtqiCpoY2j4")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }

}

class ExpedienteTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun CrearExpediente() {
        composeTestRule.setContent {
            MaterialTheme {
                ExpedienteScreen()
            }
        }

        composeTestRule.onNodeWithTag("NombreTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Juan Perez")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("MotivTextField")
            .assertExists("El campo de texto para el motivo de consulta no existe.")
            .performTextInput("Dolor abdominal severo")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("ExplFisTextField")
            .assertExists("El campo de texto para la exploración física no existe.")
            .performTextInput("Presión arterial alta, pulso irregular")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("DiagTextField")
            .assertExists("El campo de texto para el diagnóstico no existe.")
            .performTextInput("Apendicitis aguda")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("TrataTextField")
            .assertExists("El campo de texto para el tratamiento no existe.")
            .performTextInput("Cirugía de emergencia para extirpación del apéndice")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("ExmLabTextField")
            .assertExists("El campo de texto para los exámenes de laboratorio no existe.")
            .performTextInput("Hemograma completo, análisis de orina")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("PronoTextField")
            .assertExists("El campo de texto para el pronóstico no existe.")
            .performTextInput("Pronóstico favorable después de la cirugía")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("RegistrarButton")
            .performClick()
        Thread.sleep(5000)

    }
    @Test
    fun MuestraExpediente() {
        composeTestRule.setContent {
            MaterialTheme {
                MostrarExpedientes()
            }
        }

        Thread.sleep(5000)
    }
    @Test
    fun testEliminarExpediente() {

        composeTestRule.setContent {
            MaterialTheme {
                MostrarExpedientes()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("EliminaExp_JAQUFvDlsKbKrenOdxnE")
            .performClick()
        composeTestRule.waitForIdle()

    }
    @Test
    fun testEditarExpediente() {
        composeTestRule.setContent {
            MaterialTheme {
                MostrarExpedientes()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("ActualizarHGz0eXon5SQY1bTVpUsG")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }


}

class CitasTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun RegristraCita() {
        composeTestRule.setContent {
            MaterialTheme {
                Citas()
            }
        }
        composeTestRule.onNodeWithTag("prueba")

            .performTextInput("03/06/2024")
        composeTestRule.waitForIdle()
        Thread.sleep(5000)


    }
    @Test
    fun MuestraCita() {
        composeTestRule.setContent {
            MaterialTheme {
                mostrar()
            }
        }
        Thread.sleep(5000)
    }
    @Test
    fun testEditarCita() {
        composeTestRule.setContent {
            MaterialTheme {
                mostrar()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("ActualizarFWMTDLVkOY65713USLbW")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }

    @Test
    fun testEliminarCita() {
        composeTestRule.setContent {
            MaterialTheme {
                mostrar()
            }
        }
        Thread.sleep(5000)
        composeTestRule.onNodeWithTag("EliminaFWMTDLVkOY65713USLbW")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }

}
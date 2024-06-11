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
        //Nombre
        composeTestRule.onNodeWithTag("NombreTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Juan Perez")
        composeTestRule.waitForIdle()
        //motivo_consulta
        composeTestRule.onNodeWithTag("MotivTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Chorro")
        composeTestRule.waitForIdle()
        //exploracion_fisica
        composeTestRule.onNodeWithTag("ExplFisTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("no se que madres significa xd")
        composeTestRule.waitForIdle()
        //diagnostico
        composeTestRule.onNodeWithTag("DiagTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Posible sida")
        composeTestRule.waitForIdle()
        //tratamiento
        composeTestRule.onNodeWithTag("TrataTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("Masaje testicular")
        composeTestRule.waitForIdle()
        //examenes_lab
        composeTestRule.onNodeWithTag("ExmLabTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("???")
        composeTestRule.waitForIdle()
        //pronostico
        composeTestRule.onNodeWithTag("PronoTextField")
            .assertExists("El campo de texto para el nombre no existe.")
            .performTextInput("La muerte")
        composeTestRule.waitForIdle()
        //Regitro
        composeTestRule.onNodeWithTag("RegistrarButton")
            .performClick()
        Thread.sleep(5000)

    }
    @Test
    fun MuestraExpediente() {
        composeTestRule.setContent {
            MaterialTheme {
                ExpedienteScreen()
            }
        }
        composeTestRule.onNodeWithTag("MostrarButton")
            .performClick()
        Thread.sleep(5000)
    }
    @Test
    fun testEliminarExpediente() {
        // Lanza la actividad de MostrarExpedientesActivity
        composeTestRule.setContent {
            MaterialTheme {
                MostrarExpedientes()
            }
        }
      //No pude
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
                mostrar()
            }
        }
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
}
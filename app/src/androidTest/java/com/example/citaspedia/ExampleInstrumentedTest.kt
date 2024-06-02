package com.example.citaspedia

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.compose.material3.MaterialTheme
import com.example.citaspedia.ui.Citaspedia
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*

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
class CitaspediaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCitaspediaForm() {
        composeTestRule.setContent {
            MaterialTheme {
                Citaspedia()
            }
        }

        // Input name
        composeTestRule.onNodeWithTag("nombreTextField")
            .performTextInput("Juan Perez")

        // Input age
        composeTestRule.onNodeWithTag("edadTextField")
            .performTextInput("30")

        // Input gender
        composeTestRule.onNodeWithTag("sexoTextField")
            .performTextInput("Masculino")

        // Input curp
        composeTestRule.onNodeWithTag("curpTextField")
            .performTextInput("CURP123456")

        // Input motivo ingreso
        composeTestRule.onNodeWithTag("moingTextField")
            .performTextInput("Consulta general")

        // Input motivo lesion
        composeTestRule.onNodeWithTag("motlesTextField")
            .performTextInput("Accidente de tráfico")

        // Input peso
        composeTestRule.onNodeWithTag("pesoTextField")
            .performTextInput("70")

        // Input temperatura
        composeTestRule.onNodeWithTag("tempTextField")
            .performTextInput("36.5")

        // Input talla
        composeTestRule.onNodeWithTag("tallaTextField")
            .performTextInput("175")

        // Click Aceptar button
        composeTestRule.onNodeWithTag("AceptarButton")
            .performClick()

        // Optionally, you can add assertions to verify the state after the button click
        // For example, check if the error dialog is shown or not
        // composeTestRule.onNodeWithTag("errorDialog").assertIsDisplayed()
    }
}
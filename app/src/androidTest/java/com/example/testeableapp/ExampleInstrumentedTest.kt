package com.example.testeableapp

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testeableapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testRedondeoCambiaCalculo() {
        composeTestRule.onNodeWithTag("billAmount").performTextInput("100.50")

        val propinaOriginal = composeTestRule.onNodeWithTag("tipAmount")
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        composeTestRule.onNodeWithTag("roundUpCheckbox").performClick()

        val propinaRedondeada = composeTestRule.onNodeWithTag("tipAmount")
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        assert(propinaOriginal == "Propina: \$15.08")
        assert(propinaRedondeada == "Propina: \$16.00")
    }

    @Test
    fun testSliderCambiaPorcentaje() {
        composeTestRule.onNodeWithTag("billAmount").performTextInput("200.00")

        composeTestRule.onNodeWithTag("tipSlider")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(20f) }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onNodeWithTag("tipAmount")
                .fetchSemanticsNode()
                .config[SemanticsProperties.Text]
                .joinToString()
                .contains("Propina: $40.00")
        }

        composeTestRule.onNodeWithTag("tipAmount").assertTextContains("Propina: $40.00")
    }


    @Test
    fun testElementosVisibles() {
        composeTestRule.onNodeWithTag("billAmount").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tipSlider").assertIsDisplayed()
        composeTestRule.onNodeWithTag("roundUpCheckbox").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tipAmount").assertIsDisplayed()
        composeTestRule.onNodeWithTag("totalPerPerson").assertIsDisplayed()
    }

    /*
        Cambio en el número de personas: se verifica que al incrementar la cantidad
        de personas con el botón "+", el total por persona se actualiza dinámicamente.
        Esto asegura el correcto comportamiento de la división del total.
    */
    @Test
    fun testCambiaNumeroDePersonasActualizaTotal() {
        composeTestRule.onNodeWithTag("billAmount").performTextInput("150.00")

        val totalInicial = composeTestRule.onNodeWithTag("totalPerPerson")
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        composeTestRule.onNodeWithText("+").performClick()

        val totalDespues = composeTestRule.onNodeWithTag("totalPerPerson")
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        assert(totalInicial == "Total por persona: \$172.50") //150 + 22.50 (15%) / 1
        assert(totalDespues == "Total por persona: \$86.25") //172.50 / 2
    }

    /*
        Cambio del monto de la cuenta: se asegura que al ingresar un nuevo monto
        (por ejemplo, 200), el valor de la propina se recalcula correctamente.
        Esto valida que la app responde adecuadamente a cambios en su input principal.
    */
    @Test
    fun testCambiarMontoActualizaPropina() {
        composeTestRule.onNodeWithTag("billAmount").performTextInput("200.00")
        composeTestRule.onNodeWithTag("tipAmount").assertTextContains("30.00")
        composeTestRule.onNodeWithTag("billAmount").performTextReplacement("350.00")
        composeTestRule.onNodeWithTag("tipAmount").assertTextContains("52.50")
    }
}

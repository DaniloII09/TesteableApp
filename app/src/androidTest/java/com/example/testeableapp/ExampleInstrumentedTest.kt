package com.example.testeableapp

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testeableapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_elementosVisibles() {
        composeTestRule.onNodeWithText("Redondear propina").assertIsDisplayed()
        composeTestRule.onNode(hasTextStartingWith("Propina")).assertIsDisplayed()
        composeTestRule.onNode(hasTextStartingWith("Total por persona")).assertIsDisplayed()
    }


    @Test
    fun test_redondeoCambiaCalculo() {
        val propinaOriginal = composeTestRule
            .onNode(hasTextStartingWith("Propina"))
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        composeTestRule.onNodeWithText("Redondear propina").performClick()

        val propinaRedondeada = composeTestRule
            .onNode(hasTextStartingWith("Propina"))
            .assertExists()
            .fetchSemanticsNode().config[SemanticsProperties.Text].joinToString()

        assert(propinaOriginal != propinaRedondeada)
    }
}


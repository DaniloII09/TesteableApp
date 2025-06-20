package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ceil

class ExampleUnitTest {

    @Test
    fun testCalculoConVeintePorciento() {
        val amount = 100.0
        val tipPercent = 20
        val expectedTip = 20.0

        val actualTip = calculateTip(amount, tipPercent, false)

        assertEquals(expectedTip, actualTip, 0.01)
    }

    @Test
    fun testCalculoConQuincePorcientoYRedondeo() {
        val amount = 10.0
        val tipPercent = 15
        val expectedTip = 2.0

        val actualTip = calculateTip(amount, tipPercent, true)

        assertEquals(expectedTip, actualTip, 0.01)
    }

    @Test
    fun testCantidadNegativa() {
        val amount = -50.0
        val tipPercent = 20

        val actualTip = calculateTip(amount, tipPercent, false)

        assertEquals(0.0, actualTip, 0.01)
    }

    @Test
    fun testTotalPorPersona() {
        val amount = 100.0
        val tipPercent = 20
        val numberOfPeople = 4
        val expectedTotal = 120.0
        val expectedPerPerson = expectedTotal / numberOfPeople

        val actualPerPerson = (amount + calculateTip(amount, tipPercent, false)) / numberOfPeople

        assertEquals(expectedPerPerson, actualPerPerson, 0.01)
    }

    /*
        Monto de cuenta igual a cero: garantiza que la propina calculada sea 0,
        reforzando el control de entradas inválidas o vacías.
    */
    @Test
    fun testMontoCeroDaPropinaCero() {
        val amount = 0.0
        val tipPercent = 25

        val result = calculateTip(amount, tipPercent, false)

        assertEquals(0.0, result, 0.01)
    }

    /*
        Porcentaje de propina igual a cero: valida que incluso con un monto válido,
        si el porcentaje es 0%, la propina sea 0, asegurando un comportamiento lógico
        y esperado.
    */
    @Test
    fun testPorcentajeCeroDaPropinaCero() {
        val amount = 150.0
        val tipPercent = 0

        val result = calculateTip(amount, tipPercent, false)

        assertEquals(0.0, result, 0.01)
    }
}

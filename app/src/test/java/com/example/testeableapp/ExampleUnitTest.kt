package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun test_calculoCon20Porciento() {
        val amount = 100.0
        val tipPercent = 20
        val expectedTip = 20.0
        val actualTip = calculateTip(amount, tipPercent, false)
        assertEquals(expectedTip, actualTip, 0.01)
    }

    @Test
    fun test_calculoCon15PorcientoYRedondeo() {
        val amount = 100.0
        val tipPercent = 15
        val expectedTip = 15.0
        val actualTip = calculateTip(amount, tipPercent, true)
        assertEquals(Math.ceil(expectedTip), actualTip, 0.01)
    }

    @Test
    fun test_cantidadNegativa() {
        val amount = -50.0
        val tipPercent = 20
        val actualTip = calculateTip(amount, tipPercent, false)
        assertEquals(0.0, actualTip, 0.01)
    }

    @Test
    fun test_totalPorPersona() {
        val amount = 100.0
        val tipPercent = 20
        val numberOfPeople = 4
        val total = amount + calculateTip(amount, tipPercent, false)
        val expectedPerPerson = total / numberOfPeople
        val actualPerPerson = (amount + calculateTip(amount, tipPercent, false)) / numberOfPeople
        assertEquals(expectedPerPerson, actualPerPerson, 0.01)
    }
}

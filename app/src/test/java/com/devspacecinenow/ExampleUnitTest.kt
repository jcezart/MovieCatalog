package com.devspacecinenow

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        // GWT

        // Given
        val oneNum = 2
        val twoNum = 3

        // When
        val sum = oneNum + twoNum

        // Then
        val expected = 5
        assertEquals(expected, sum)
    }
}
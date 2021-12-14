package com.example.meliapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun formatAsCurrency_validIntAsInput_returnCorrectFormated() {
        val result = 100.formatAsCurrency()
        assertThat(result).isEqualTo("MX\$100.00")
    }
}
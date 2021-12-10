package com.example.meliapp.di

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

private const val EXPECTED_URL = "https://api.mercadolibre.com/"

class NetworkProvidesTest {

    private lateinit var systemUnderTest: NetworkProvides

    @Before
    fun setUp() {
        systemUnderTest = NetworkProvides()
    }

    @Test
    fun retrofitInstance_baseUrl_isUrlExpected() {
        val retrofitInstance = systemUnderTest.retrofitInstance()
        assertThat(retrofitInstance.baseUrl().toString()).isEqualTo(EXPECTED_URL)
    }
}
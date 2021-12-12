package com.example.meliapp.di

import com.example.meliapp.data.repository.MeLiRepository
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Retrofit

private const val EXPECTED_URL = "https://api.mercadolibre.com/"

class NetworkProvidesTest {

    private val systemUnderTest: NetworkProvides = NetworkProvides()
    private val mockOkHttpClient: OkHttpClient = mock()
    private val mockRetrofit: Retrofit = mock()
    private val mockMeLiRepository: MeLiRepository = mock()

    @Test
    fun providesRetrofitInstance_baseUrl_isUrlExpected() {
        val retrofitInstance = systemUnderTest.providesRetrofitInstance(mockOkHttpClient)
        assertThat(retrofitInstance.baseUrl().toString()).isEqualTo(EXPECTED_URL)
    }

    @Test
    fun providesOkHttpClientInstance_getInterceptors_hasInterceptors() {
        val okHttpClientInstance = systemUnderTest.providesOkHttpClient()
        assertThat(okHttpClientInstance.interceptors).hasSize(1)
    }

    @Test
    fun providesOkHttpClientInstance_getInterceptors_firstIsHttpLogger() {
        val okHttpClientInstance = systemUnderTest.providesOkHttpClient()
        val firstInterceptor = okHttpClientInstance.interceptors.first()
        assertThat(firstInterceptor).isInstanceOf(HttpLoggingInterceptor::class.java)
    }

    @Test
    fun providesMeliRepositoryInstance_getInstance_instanceOfMeliRepository() {
        whenever(mockRetrofit.create(MeLiRepository::class.java)).thenReturn(mockMeLiRepository)
        val meliRepository = systemUnderTest.providesMeliRepositoryInstance(mockRetrofit)
        assertThat(meliRepository).isInstanceOf(MeLiRepository::class.java)
    }
}
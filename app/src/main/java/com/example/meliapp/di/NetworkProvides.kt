package com.example.meliapp.di

import com.example.meliapp.BuildConfig
import com.example.meliapp.data.api.MeLiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkProvides {

    private val requestTimeout = Duration.ofSeconds(30)

    @Singleton
    @Provides
    fun providesRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(requestTimeout)
            .writeTimeout(requestTimeout)
            .addInterceptor(NetworkInterceptor.getInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providesMeliRepositoryInstance(retrofit: Retrofit): MeLiApi {
        return retrofit.create(MeLiApi::class.java)
    }
}
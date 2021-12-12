package com.example.meliapp.di

import okhttp3.logging.HttpLoggingInterceptor

object NetworkInterceptor {

    fun getInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
}
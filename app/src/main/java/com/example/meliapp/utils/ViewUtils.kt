package com.example.meliapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.getInflater(): LayoutInflater {
    return LayoutInflater.from(context)
}
package com.example.meliapp.utils

import java.text.NumberFormat

fun Number.formatAsCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}
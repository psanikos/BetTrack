package com.simpletech.data.helpers

fun String?.orEmpty(): String = this ?: ""

fun <T> List<T>?.orEmpty(): List<T> = this ?: listOf()
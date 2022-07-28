package com.example.github.extension

suspend fun <T> tryWith(call: suspend () -> T): T? {
    return try {
        call.invoke()
    } catch (e: Exception) {
        null
    }
}

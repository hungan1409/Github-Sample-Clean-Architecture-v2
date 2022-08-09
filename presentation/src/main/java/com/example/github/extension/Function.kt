package com.example.github.extension

suspend fun <T> tryWithSuspend(call: suspend () -> T): T? {
    return try {
        call.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

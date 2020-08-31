package com.alexyndrik.pikabutest.model

data class PikabuResponse<T>(val response: T? = null, val error: Exception? = null)
package com.poncegamez.ricknmortyapp.result

sealed class Results<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Results<T>(data)
    class Success<T>(data: T?): Results<T>(data)
    class Error<T>(message: String, data: T? = null): Results<T>(data, message)
}
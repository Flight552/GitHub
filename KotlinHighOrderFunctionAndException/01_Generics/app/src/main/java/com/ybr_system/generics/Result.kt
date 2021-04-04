package com.ybr_system.generics

sealed class Result<T, R> {

}

data class Success<T, R>(val _true: T) : Result<T, R>() {
    fun <T: Number> result (number: T, any: String) : Result<Number, String> {
        return Success(number)
    }
}

data class Error<T, R>(val _false: R) : Result<T, R>() {
    fun <T: Any> result (number: T, any: String) : Result<Any, String> {
        return Error(any)
    }
}




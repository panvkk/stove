package com.example.stove.core

sealed interface Resource<out T> {
    data class LOADING<out T>(val data: T? = null) : Resource<T>
    data class SUCCESS<out T>(val data: T) : Resource<T>
    data class FAILURE(val error: Throwable) : Resource<Nothing>
}
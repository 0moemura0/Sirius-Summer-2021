package com.example.tinkoffproject

sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class ErrorState(val exception: Throwable?) : State<Nothing>()
    data class DataState<T>(val data: T) : State<T>()
}
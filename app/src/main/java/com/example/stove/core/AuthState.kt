package com.example.stove.core

sealed interface AuthState {
    data object Authenticated : AuthState
    data object Unauthenticated: AuthState
}
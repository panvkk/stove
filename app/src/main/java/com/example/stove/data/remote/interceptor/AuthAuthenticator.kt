package com.example.stove.data.remote.interceptor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthAuthenticator @Inject constructor(
    private val applicationScope: CoroutineScope
) : Authenticator {
    private val _authEventFlow = MutableSharedFlow<Unit>()
    val authEventFlow: SharedFlow<Unit> = _authEventFlow

    override fun authenticate(route: Route?, response: Response): Request? {
        if(response.code == 401) {
            applicationScope.launch {
                _authEventFlow.emit(Unit)
            }

            return null
        }
        return null
    }
}
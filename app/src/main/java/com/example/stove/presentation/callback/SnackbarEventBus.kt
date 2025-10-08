package com.example.stove.presentation.callback

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.stove.presentation.viewmodel.SnackbarEvent
import com.example.stove.presentation.viewmodel.SnackbarEventSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarEventBus @Inject constructor(
    private val applicationScope: CoroutineScope
) : SnackbarEventSource {
    private val _snackBarEvents = Channel<SnackbarEvent>()
    val snackbarEvent = _snackBarEvents.receiveAsFlow()

    override fun postSnackbar(message: String) {
        applicationScope.launch {
            _snackBarEvents.send(SnackbarEvent.ShowSnackbar(message))
            Log.i("SnackbarEvent", "Snackbar event with message $message send.")
        }
    }
}
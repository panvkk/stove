package com.example.stove.presentation.di

import com.example.stove.presentation.callback.SnackbarEventBus
import com.example.stove.presentation.viewmodel.GlobalViewModel
import com.example.stove.presentation.viewmodel.SnackbarEventSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    fun provideSnackbarEventSource(
        eventBus: SnackbarEventBus
    ): SnackbarEventSource {
        return eventBus
    }
}
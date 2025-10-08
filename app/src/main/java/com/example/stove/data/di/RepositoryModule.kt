package com.example.stove.data.di

import com.example.stove.data.favourite.FavouriteRepository
import com.example.stove.data.favourite.FavouriteRepositoryImpl
import com.example.stove.data.repository.AuthRepositoryImpl
import com.example.stove.data.repository.DesignerRepositoryImpl
import com.example.stove.data.repository.ProfileRepositoryImpl
import com.example.stove.domain.repository.AuthRepository
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDesignerRepository(
        designerRepositoryImpl: DesignerRepositoryImpl
    ) : DesignerRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

    @Singleton
    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryRepository: ProfileRepositoryImpl
    ) : ProfileRepository

    @Singleton
    @Binds
    abstract fun bindFavouriteRepository(
        favouriteRepositoryImpl: FavouriteRepositoryImpl
    ) : FavouriteRepository
}
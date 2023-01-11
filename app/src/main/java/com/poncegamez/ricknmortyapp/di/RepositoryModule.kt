package com.poncegamez.ricknmortyapp.di

import android.content.Context
import com.poncegamez.ricknmortyapp.api.RickAndMortyApi
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.repository.impl.RickAndMortyImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {

    @Provides
    @Singleton
    fun providesRickAndMortyRepository(rickAndMortyApi: RickAndMortyApi): RickAndMortyRepository{
        return RickAndMortyImpl(rickAndMortyApi)
    }

}
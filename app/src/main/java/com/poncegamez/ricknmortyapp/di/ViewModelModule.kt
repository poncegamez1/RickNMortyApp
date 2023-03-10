package com.poncegamez.ricknmortyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)

object ViewModelModule {

    @Provides
    @Singleton
    fun providesCoroutineContext(): CoroutineContext{
        return Dispatchers.IO
    }
}
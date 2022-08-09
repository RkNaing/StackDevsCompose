package com.rahulkumar.stackdevs.di.modules

import com.rahulkumar.stackdevs.data.remote.repositories.DevsRepositoryImpl
import com.rahulkumar.stackdevs.domain.repository.DevsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindDevsRepository(impl: DevsRepositoryImpl): DevsRepository

}
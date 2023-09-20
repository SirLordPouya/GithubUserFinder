package com.pouyaheydari.github.userfinder.data.di

import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindsGithubUserFinderRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}
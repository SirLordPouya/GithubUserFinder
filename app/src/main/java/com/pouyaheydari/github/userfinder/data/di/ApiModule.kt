package com.pouyaheydari.github.userfinder.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    /**
     * Provides JsonConvertor
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(converter: Converter.Factory): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .build()
    }
}

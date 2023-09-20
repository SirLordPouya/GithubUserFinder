package com.pouyaheydari.github.userfinder.data.di

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    /**
     * Provides JsonConvertor
     */
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideResultCallAdapterFactory(): CallAdapter.Factory {
        return ResultCallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(converter: Converter.Factory, callAdapter: CallAdapter.Factory): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .addCallAdapterFactory(callAdapter)
            .build()
    }
}

package com.pouyaheydari.github.userfinder.features.search.data.di

import com.pouyaheydari.github.userfinder.features.search.data.UsersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class SearchUserModule {
    @Provides
    fun provideSearchUserRemote(retrofit: Retrofit): UsersApiService = retrofit.create(
        UsersApiService::class.java)
}

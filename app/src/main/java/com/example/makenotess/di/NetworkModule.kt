package com.example.makenotess.di

import com.example.makenotess.api.AuthInceptor
import com.example.makenotess.api.NoteAPI
import com.example.makenotess.api.UserAPI
import com.example.makenotess.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NoteAPI {
        return retrofitBuilder.client(okHttpClient).build().create(NoteAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideOkhttpClient(authInceptor: AuthInceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInceptor).build()
    }


}
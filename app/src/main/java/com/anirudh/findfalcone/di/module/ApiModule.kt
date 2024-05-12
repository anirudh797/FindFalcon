package com.anirudh.findfalcone.di.module

import com.anirudh.findfalcone.data.remote.FalconApi
import com.anirudh.findfalcone.data.remote.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    /*
   * The method returns the Gson object
   * */
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideFindFalconApiService(gson: Gson): FalconApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(RetrofitInstance.BASE_URL)
            .build().create(FalconApi::class.java)
    }

}
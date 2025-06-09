package com.cabila0046.assessment3.network

import com.cabila0046.assessment3.model.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://plantatious.sendiko.my.id/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface TumbuhanApiService {
    @GET("plants")
    @Headers("Accept: application/json")
    suspend fun getTumbuhan(@Query("userId") userId: String): ApiResponse
}

object TumbuhanApi {
    val service: TumbuhanApiService by lazy {
        retrofit.create(TumbuhanApiService::class.java)
    }

}
enum class ApiStatus { LOADING, SUCCESS, FAILED }
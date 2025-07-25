package com.cabila0046.assessment3.network

import com.cabila0046.assessment3.model.ApiResponse
import com.cabila0046.assessment3.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
    suspend fun getTumbuhan(@Query("userId") userId: String): ApiResponse

    @Multipart
    @POST("plants")
    suspend fun postTumbuhan(
        @Part("userId") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("habitat") habitat: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("plants/{id}")
    suspend fun deleteTumbuhan(
        @Path("id") id: String
    ): Unit

    @Multipart
    @PUT("plants/{id}")
    suspend fun updateTumbuhan(
        @Path("id") id: String,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("habitat") habitat: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Unit

}

object TumbuhanApi {
    val service: TumbuhanApiService by lazy {
        retrofit.create(TumbuhanApiService::class.java)
    }

}
enum class ApiStatus { LOADING, SUCCESS, FAILED }
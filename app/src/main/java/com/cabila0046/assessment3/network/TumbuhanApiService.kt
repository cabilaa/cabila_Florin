package com.cabila0046.assessment3.network

import androidx.compose.foundation.Image
import androidx.compose.ui.input.pointer.PointerId
import com.cabila0046.assessment3.model.Tumbuhan
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com" +
        "/indraazimi/mobpro1-compose/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TumbuhanApiService {
    @GET("static-api.json")
    suspend fun getTumbuhan(): List<Tumbuhan>
}
object TumbuhanApi {
    val service: TumbuhanApiService by lazy {
        retrofit.create(TumbuhanApiService::class.java)
    }
    fun getTumbuhanUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}
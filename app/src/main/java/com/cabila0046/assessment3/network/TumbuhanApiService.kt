package com.cabila0046.assessment3.network

import androidx.compose.foundation.Image
import androidx.compose.ui.input.pointer.PointerId
import com.cabila0046.assessment3.model.Tumbuhan
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://documenter.getpostman.com/view/21050563/2sB2x3mt7D"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TumbuhanApiService {
    @GET("tumbuhan.php")
    suspend fun getTumbuhan(): List<Tumbuhan>
}
object TumbuhanApi {
    val service: TumbuhanApiService by lazy {
        retrofit.create(TumbuhanApiService::class.java)
    }
    fun getTumbuhanUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }
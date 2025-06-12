package com.cabila0046.assessment3.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabila0046.assessment3.model.Tumbuhan
import com.cabila0046.assessment3.network.ApiStatus
import com.cabila0046.assessment3.network.TumbuhanApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainViewModel : ViewModel(){

    var data = mutableStateOf(emptyList<Tumbuhan>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set


    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.SUCCESS
            try {
                val response = TumbuhanApi.service.getTumbuhan(userId)
                data.value = response.plants
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
    fun saveData(userId: String?, name: String, species: String, habitat: String, bitmap: Bitmap) {
        if (userId.isNullOrBlank()) {
            errorMessage.value = "Anda harus login terlebih dahulu"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TumbuhanApi.service.postTumbuhan(
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    species.toRequestBody("text/plain".toMediaTypeOrNull()),
                    habitat.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "201") {
                    Log.d("MainViewmodel", "Respon status: ${result.message}")
                    retrieveData(userId)
                }
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"

            }
        }
    }

    fun deleteData(userId: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                TumbuhanApi.service.deleteTumbuhan(id)
                Log.d("MainViewModel", "Data berhasil dihapus dengan ID: $id")
                retrieveData(userId)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Gagal menghapus data: ${e.message}")
            }
        }
    }

    fun updateData(userId: String, id: String, name: String, species: String, habitat: String, bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val speciesPart = species.toRequestBody("text/plain".toMediaTypeOrNull())
                val habitatPart = habitat.toRequestBody("text/plain".toMediaTypeOrNull())

                val image = bitmap?.let {
                    val file = File.createTempFile("image", ".jpg")
                    val out = FileOutputStream(file)
                    it.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()

                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", file.name, requestFile)
                }

                TumbuhanApi.service.updateTumbuhan(id, namePart, speciesPart, habitatPart, image)

                Log.d("MainViewModel", "Data berhasil diupdate untuk ID: $id")
                retrieveData(userId)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Gagal update data: ${e.message}")
            }

        }

    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody
        )
    }
    fun clearMessage() { errorMessage.value = null }

}
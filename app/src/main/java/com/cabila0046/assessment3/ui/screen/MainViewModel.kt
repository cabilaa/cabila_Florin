package com.cabila0046.assessment3.ui.screen

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


class MainViewModel : ViewModel(){

    var data = mutableStateOf(emptyList<Tumbuhan>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }
    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.SUCCESS
            try {
//                val result = TumbuhanApi.service.getTumbuhan("null")
//                        Log.d("MainViewModel", "Success: $result")
                val response = TumbuhanApi.service.getTumbuhan("null")
                data.value = response.plants
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}
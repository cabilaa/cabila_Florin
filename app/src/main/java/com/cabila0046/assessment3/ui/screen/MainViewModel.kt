package com.cabila0046.assessment3.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabila0046.assessment3.model.Tumbuhan
import com.cabila0046.assessment3.network.TumbuhanApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(){

    var data = mutableStateOf(emptyList<Tumbuhan>())

    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
               data.value = TumbuhanApi.service.getTumbuhan()
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}
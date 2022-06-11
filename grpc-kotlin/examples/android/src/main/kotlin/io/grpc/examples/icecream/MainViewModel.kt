package io.grpc.examples.icecream

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val service by lazy { IceCreamService() }

    var uiState by mutableStateOf(UiState())
        private set


    fun loadCones() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                delay(1000)
                val coneList = service.getCones("id")
                uiState = uiState.copy(cones = coneList)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Something went wrong. Try again")
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }

    fun loadFlavors() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                delay(1000)
                val flavorList = service.getFlavors("id")
                uiState = uiState.copy(flavors = flavorList)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Something went wrong. Try again")
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        service.close()
    }
}
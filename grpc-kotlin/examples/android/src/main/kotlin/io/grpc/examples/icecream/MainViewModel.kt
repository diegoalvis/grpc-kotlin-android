package io.grpc.examples.icecream

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.grpc.examples.icecream.rest.IceCreamRestService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

        private val service by lazy { IceCreamRpcService() }
//    private val service by lazy { IceCreamRestService() }

    var uiState by mutableStateOf(UiState())
        private set


    fun loadCones() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val coneList = service.getCones("id")
                uiState = uiState.copy(cones = coneList)
            } catch (e: Exception) {
                Log.e("ALIVS", e.message!!)
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

    fun setCone(cone: Cone) {
        uiState = uiState.copy(selectedCone = cone)
    }

    fun setFlavor(flavor: Flavor) {
        if (uiState.selectedCone == null) return

        val newList = uiState.selectedFlavors.run {
            when (size) {
                3 -> listOf(flavor)
                else -> plus(flavor)
            }
        }
        uiState = uiState.copy(selectedFlavors = newList)
    }
}
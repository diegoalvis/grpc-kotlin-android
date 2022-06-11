package io.grpc.examples.icecream


data class UiState(
        val flavors: List<Flavor> = emptyList(),
        val cones: List<Cone> = emptyList(),
        val loading: Boolean = false,
        val errorMessage: String = "",
        val selectedCone: Cone? = null,
        val selectedFlavors: List<Flavor> = emptyList()

)
package io.grpc.examples.icecream


data class UiState(
        val flavors: List<Flavor> = emptyList(),
        val cones: List<Cone> = emptyList(),
        val loading: Boolean = false,
        val errorMessage: String = "",
        val iceCream: IceCream? = null

)

data class IceCream(
        val cone: Cone,
        val flavors: List<Flavor>
)
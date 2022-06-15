package io.grpc.examples.icecream.rest

import io.grpc.examples.icecream.Cone
import io.grpc.examples.icecream.Flavor

data class ConeRest(
        val type: Int,
        val imageUrl: String,
        val enabled: Boolean,
)

fun ConeRest.toCone(): Cone {
    return Cone.newBuilder()
            .setType(type)
            .setImageUrl(imageUrl)
            .setEnabled(enabled)
            .build()
}

data class FlavorRest(
        val name: String,
        val imageUrl: String,
        val description: String,
        val price: Double,
        val barcode: String,
        val enabled: Boolean,
)

fun FlavorRest.toFlavor(): Flavor {
    return Flavor.newBuilder()
            .setName(name)
            .setImageUrl(imageUrl)
            .setDescription(description)
            .setPrice(price)
            .setBarcode(barcode)
            .setEnabled(enabled)
            .build()
}
package io.grpc.examples.icecream

import com.google.protobuf.util.JsonFormat

object Database {
    fun cones(): List<Cone> {
        return javaClass.getResourceAsStream("ice_cream.json")?.use {
            val databaseBuilder = IceCreamDatabase.newBuilder()
            JsonFormat.parser().merge(it.reader(), databaseBuilder)
            databaseBuilder.build().coneList
        } ?: emptyList()
    }

    fun flavors(): List<Flavor> {
        return javaClass.getResourceAsStream("ice_cream.json")?.use {
            val databaseBuilder = IceCreamDatabase.newBuilder()
            JsonFormat.parser().merge(it.reader(), databaseBuilder)
            databaseBuilder.build().flavorList
        } ?: emptyList()
    }
}

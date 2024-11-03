package com.diegoalvis.example.grpc

import com.google.protobuf.util.JsonFormat

object TravelDatabase {
    fun destinations(): List<Travel.Destination> {
        return javaClass.getResourceAsStream("destination.json")?.use {
            val databaseBuilder = Travel.TravelDatabase.newBuilder()
            JsonFormat.parser().merge(it.reader(), databaseBuilder)
            databaseBuilder.build().destinationList
        } ?: emptyList()
    }
}
package com.diegoalvis.example.grpc

import io.grpc.Server
import io.grpc.ServerBuilder

class TravelServer(private val port: Int) {

    private val destinationsData: Collection<Travel.Destination> by lazy { TravelDatabase.destinations() }

    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(
            TravelService(
                destinationsData = destinationsData,
            )
        )
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@TravelServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class TravelService(
        private val destinationsData: Collection<Travel.Destination>,
    ) : TravelServiceGrpcKt.TravelServiceCoroutineImplBase() {


        override suspend fun getDestinations(request: Travel.GetDestinationsRequest) = getDestinationsResponse {
            destinations.addAll(destinationsData)
        }

    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = TravelServer(port)
    server.start()
    server.blockUntilShutdown()
}

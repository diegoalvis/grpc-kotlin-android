package io.grpc.examples.icecream

import io.grpc.Server
import io.grpc.ServerBuilder

class IceCreamServer(private val port: Int) {

    private val cones: Collection<Cone> = Database.cones()
    private val flavors: Collection<Flavor> = Database.flavors()

    val server: Server = ServerBuilder
            .forPort(port)
            .addService(IceCreamService())
            .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
                Thread {
                    println("*** shutting down gRPC server since JVM is shutting down")
                    this@IceCreamServer.stop()
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

    internal class IceCreamService : IceCreamGrpcKt.IceCreamCoroutineImplBase() {
        override suspend fun getCones(request: Request): ConesReply = conesReply {
            cones = cones
        }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = IceCreamServer(port)
    server.start()
    server.blockUntilShutdown()
}

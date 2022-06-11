package io.grpc.examples.icecream

import android.net.Uri
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.io.Closeable


class IceCreamClient : Closeable {

    private val channel = let {
        val uri = Uri.parse(SERVER_URL)
        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if (uri.scheme == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        builder.executor(Dispatchers.IO.asExecutor()).build()
    }

    private val coroutineStub = IceCreamGrpcKt.IceCreamCoroutineStub(channel)


    suspend fun getCones(userId: String): List<Cone> {
        val request = request {
            this.userId = userId
        }
        return coroutineStub.getCones(request).coneList
    }

    suspend fun getFlavors(userId: String): List<Flavor> {
        val request = request {
            this.userId = userId
        }
        return coroutineStub.getFlavors(request).flavorList
    }

    override fun close() {
        channel.shutdownNow()
    }

    companion object {
        private const val SERVER_URL = "http://10.0.2.2:50051/"
    }
}
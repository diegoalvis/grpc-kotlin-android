package io.grpc.examples.icecream.rest

import io.grpc.examples.icecream.Cone
import io.grpc.examples.icecream.Flavor
import io.grpc.examples.icecream.rest.RetrofitService.Companion.REST_SERVER_BASE_URL
import io.grpc.examples.icecream.rest.dto.ConeRest
import io.grpc.examples.icecream.rest.dto.FlavorRest
import io.grpc.examples.icecream.rest.dto.toCone
import io.grpc.examples.icecream.rest.dto.toFlavor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class IceCreamRestService {

    private val service by lazy { createRetrofitService() }

    suspend fun getCones(userId: String): List<Cone> {
        return service.getCones(userId).map(ConeRest::toCone)
    }

    suspend fun getFlavors(userId: String): List<Flavor> {
        return service.getFlavors(userId).map(FlavorRest::toFlavor)
    }

    fun close() {
        // TODO unimplemented
    }

}


fun createRetrofitService(): RetrofitService =
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(REST_SERVER_BASE_URL)
                .build()
                .create(RetrofitService::class.java)

package io.grpc.examples.icecream.rest

import io.grpc.examples.icecream.rest.dto.ConeRest
import io.grpc.examples.icecream.rest.dto.FlavorRest
import retrofit2.http.GET
import retrofit2.http.Query


// TODO test server use https://mocki.io
interface RetrofitService {

    companion object {
        const val REST_SERVER_BASE_URL = "https://mocki.io"
        private const val conesPath = "/v1/7eafdb5c-c9aa-4abb-9af9-e93abbb859ed"
        private const val flavorPath = "/v1/363e85bd-c38d-4ebc-ba93-d6565905db24"
    }

    @GET(conesPath)
    suspend fun getCones(@Query("user_id") userId: String): List<ConeRest>

    @GET(flavorPath)
    suspend fun getFlavors(@Query("user_id") userId: String): List<FlavorRest>

}
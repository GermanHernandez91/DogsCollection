package com.example.dogscollection.data.network

import com.example.dogscollection.models.Breeds
import com.example.dogscollection.models.Dogs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsApi {

    @GET("/api/breeds/list/all")
    suspend fun getBreedsList(): Response<Breeds>

    @GET("/api/breed/{breed}/images/random/10")
    suspend fun getRandomDogsByBreed(
        @Path("breed") breed: String
    ): Response<Dogs>
}
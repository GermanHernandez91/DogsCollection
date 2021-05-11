package com.example.dogscollection.data

import com.example.dogscollection.data.network.DogsApi
import com.example.dogscollection.models.Breeds
import com.example.dogscollection.models.Dogs
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dogsApi: DogsApi
) {

    suspend fun getBreedsList(): Response<Breeds> {
        return dogsApi.getBreedsList()
    }

    suspend fun getRandomDogsByBreed(breed: String): Response<Dogs> {
        return dogsApi.getRandomDogsByBreed(breed)
    }
}
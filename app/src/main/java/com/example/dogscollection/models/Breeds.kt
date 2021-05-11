package com.example.dogscollection.models


import com.google.gson.annotations.SerializedName

data class Breeds(
    @SerializedName("message")
    val breedItem: BreedItem,
    @SerializedName("status")
    val status: String
)
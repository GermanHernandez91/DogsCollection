package com.example.dogscollection.models


import com.google.gson.annotations.SerializedName

data class Breeds(
    @SerializedName("message")
    val breedItem: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)
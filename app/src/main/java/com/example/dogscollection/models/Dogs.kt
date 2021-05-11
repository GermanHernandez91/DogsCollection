package com.example.dogscollection.models


import com.google.gson.annotations.SerializedName

data class Dogs(
    @SerializedName("message")
    val message: List<String>,
    @SerializedName("status")
    val status: String
)
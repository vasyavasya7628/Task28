package com.example.task28phones.data

import com.google.gson.annotations.SerializedName

data class DataPhones(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("type")
    val type: String
)
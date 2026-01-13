package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class SprayListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Spray>?
)

data class Spray(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("fullIcon") val fullIcon: String?,
    @SerializedName("displayIcon") val displayIcon: String?
)
package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class MapListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<MapData>?
)

data class MapData(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("splash") val splash: String?,
    @SerializedName("displayIcon") val displayIcon: String?,
    @SerializedName("coordinates") val coordinates: String?
)
package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class BundleListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Bundle>?
)

data class Bundle(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("displayIcon") val displayIcon: String?,
    @SerializedName("assetPath") val assetPath: String?
)
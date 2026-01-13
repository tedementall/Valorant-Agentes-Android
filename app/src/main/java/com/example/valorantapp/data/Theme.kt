package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class ThemeListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Theme>?
)

data class Theme(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("assetPath") val assetPath: String?
)
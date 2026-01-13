package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class PlayerCardListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<PlayerCard>?
)

data class PlayerCard(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("displayIcon") val displayIcon: String?,
    @SerializedName("smallArt") val smallArt: String?,
    @SerializedName("wideArt") val wideArt: String?,
    @SerializedName("largeArt") val largeArt: String?,
    // ESTA ES LA LÍNEA QUE FALTABA
    @SerializedName("themeUuid") val themeUuid: String?
)
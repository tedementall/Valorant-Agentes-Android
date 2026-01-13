package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class CompetitiveTierResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<CompetitiveTierEpisode>?
)

data class CompetitiveTierEpisode(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("assetObjectName") val assetObjectName: String?,
    // La API no da un "displayName" para los episodios, así que lo construiremos del assetObjectName
    @SerializedName("tiers") val tiers: List<Tier>?
)

data class Tier(
    @SerializedName("tier") val tier: Int?,
    @SerializedName("tierName") val tierName: String?,
    @SerializedName("divisionName") val divisionName: String?,
    @SerializedName("largeIcon") val largeIcon: String?,
    @SerializedName("rankTriangleUpIcon") val rankTriangleUpIcon: String?,
    @SerializedName("rankTriangleDownIcon") val rankTriangleDownIcon: String?
)
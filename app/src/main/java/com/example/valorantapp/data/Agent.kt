package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class AgentListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Agent>?
)

data class Agent(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("displayIcon") val displayIcon: String?
)
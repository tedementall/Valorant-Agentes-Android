package com.example.valorantapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ValorantApiService {

    @GET("v1/agents")
    suspend fun getAgents(
        @Query("language") lang: String,
        @Query("isPlayableCharacter") isPlayable: Boolean
    ): AgentListResponse
}
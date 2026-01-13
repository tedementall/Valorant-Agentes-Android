package com.example.valorantapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ValorantApiService {

    @GET("v1/agents")
    suspend fun getAgents(@Query("language") lang: String, @Query("isPlayableCharacter") isPlayable: Boolean): AgentListResponse

    @GET("v1/weapons")
    suspend fun getWeapons(@Query("language") lang: String): WeaponListResponse

    @GET("v1/maps")
    suspend fun getMaps(@Query("language") lang: String): MapListResponse

    @GET("v1/sprays")
    suspend fun getSprays(@Query("language") lang: String): SprayListResponse

    @GET("v1/playercards")
    suspend fun getPlayerCards(@Query("language") lang: String): PlayerCardListResponse

    @GET("v1/themes")
    suspend fun getThemes(@Query("language") lang: String): ThemeListResponse

    @GET("v1/bundles")
    suspend fun getBundles(@Query("language") lang: String): BundleListResponse

    @GET("v1/competitivetiers")
    suspend fun getCompetitiveTiers(): CompetitiveTierResponse

}
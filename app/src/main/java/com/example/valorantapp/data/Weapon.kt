package com.example.valorantapp.data

import com.google.gson.annotations.SerializedName

data class WeaponListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Weapon>?
)

data class WeaponStats(
    @SerializedName("fireRate") val fireRate: Double?,
    @SerializedName("magazineSize") val magazineSize: Int?,
    @SerializedName("equipTimeSeconds") val equipTimeSeconds: Double?,
    @SerializedName("reloadTimeSeconds") val reloadTimeSeconds: Double?
)

data class Skin(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("displayIcon") val displayIcon: String?,
    @SerializedName("themeUuid") val themeUuid: String?
)

data class Weapon(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("displayIcon") val displayIcon: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("weaponStats") val weaponStats: WeaponStats?,
    @SerializedName("skins") val skins: List<Skin>?
)
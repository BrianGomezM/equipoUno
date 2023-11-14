package com.univalle.picobotellamvvm.model

import com.google.gson.annotations.SerializedName

data class PokemonList (
    @SerializedName("pokemon")
    val pokemon: List<Pokemon>
)

data class Pokemon(
    @SerializedName("id") val id: Int,
    @SerializedName("num") val number: String,
    @SerializedName("name") val name: String,
    @SerializedName("img") val image: String,
    @SerializedName("type") val type: List<String>,
    @SerializedName("height") val height: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("candy") val candy: String,
    @SerializedName("candy_count") val candyCount: Int,
    @SerializedName("egg") val egg: String,
    @SerializedName("spawn_chance") val spawnChance: Double,
    @SerializedName("avg_spawns") val avgSpawns: Double,
    @SerializedName("spawn_time") val spawnTime: String,
    @SerializedName("multipliers") val multipliers: List<Double>?,
    @SerializedName("weaknesses") val weaknesses: List<String>,
    @SerializedName("next_evolution") val nextEvolution: List<Evolution>?,
    @SerializedName("prev_evolution") val prevEvolution: List<Evolution>?
)

data class Evolution(
    @SerializedName("num") val number: String,
    @SerializedName("name") val name: String
)
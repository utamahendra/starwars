package com.example.starwars.data.remote.response

import com.google.gson.annotations.SerializedName

data class PeopleResponse(val results: List<People>)

data class People(
    val name: String,
    val height: String,
    val mass: String,
    @SerializedName("hair_color") val hairColor: String,
    @SerializedName("skin_color") val skinColor: String,
    @SerializedName("eye_color") val eyeColor: String,
    @SerializedName("birth_year") val birthYear: String,
    val gender: String
)
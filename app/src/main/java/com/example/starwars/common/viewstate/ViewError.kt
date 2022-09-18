package com.example.starwars.common.viewstate


data class ViewError(
    val errorCode: String,
    val message: String? = null,
    val data: Any? = null
)
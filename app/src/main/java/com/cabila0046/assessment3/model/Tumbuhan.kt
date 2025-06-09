package com.cabila0046.assessment3.model


data class ApiResponse(
    val status: String,
    val message: String,
    val plants: List<Tumbuhan>
)


data class Tumbuhan(
    val name: String,
    val species: String,
    val habitat: String,
    val imageUrl: String

)

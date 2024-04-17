package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Debt(
    val id: Int,
    val name: String,
    val expiration: String,
    val amount: Double,
    val description: String,
    val relevance: Boolean,
    val idClient: Int,
)




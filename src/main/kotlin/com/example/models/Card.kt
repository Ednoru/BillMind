package com.example.models

data class Card(
    val id: Int,
    val cardName: String,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val idClient: Int,
)

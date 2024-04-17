package com.example.repositories

import com.example.models.Card

object CardRepository {
    private val cards = mutableListOf<Card>(
        Card(1, "Visa", "1234-5678-9012-3456", "12/28", "123", 1),
        Card(2, "MasterCard", "1234-5678-9012-3456", "12/28", "123", 2),
    )

    fun getCardsByClientId(clientId: Int): List<Card> {
        return cards.filter { it.idClient == clientId }
    }
}
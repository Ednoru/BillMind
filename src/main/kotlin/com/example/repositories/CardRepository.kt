package com.example.repositories

import com.example.models.Card
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object CardRepository {
    private object card : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val cardName = varchar("card_name", 100)
        val cardNumber = varchar("card_number", 100)
        val expirationDate = varchar("expiration_date", 100)
    }

    private fun resultRowToCard(row: ResultRow): Card {
        return Card(
            id = row[card.id],
            idClient = row[card.clientId],
            cardName = row[card.cardName],
            cardNumber = row[card.cardNumber],
            expirationDate = row[card.expirationDate]
        )
    }

    // Funcion que obtiene todas las tarjetas
    fun getAllCards(): List<Card> {
        return transaction {
            card.selectAll().map { resultRowToCard(it) }
        }
    }

    // Funcion que obtiene una tarjeta por su id
    fun getCardById(cardId: Int): Card? {
        return transaction {
            card.select { card.id eq cardId }.map { resultRowToCard(it) }.firstOrNull()
        }
    }

    // Funcion que agrega una tarjeta
    fun addCard(card: Card) {
        transaction {
            CardRepository.card.insert {
                it[clientId] = card.idClient
                it[cardName] = card.cardName
                it[cardNumber] = card.cardNumber
                it[expirationDate] = card.expirationDate
            }
        }
    }

    // Funcion que actualiza una tarjeta
    fun updateCard(card: Card) {
        transaction {
            CardRepository.card.update({ CardRepository.card.id eq card.id }) {
                it[clientId] = card.idClient
                it[cardName] = card.cardName
                it[cardNumber] = card.cardNumber
                it[expirationDate] = card.expirationDate
            }
        }
    }

    // Funcion que obtiene todas las tarjetas de un cliente
    fun getCardByClientId(clientId: Int): List<Card> {
        return transaction {
            card.select { card.clientId eq clientId }.map { resultRowToCard(it) }
        }
    }
}
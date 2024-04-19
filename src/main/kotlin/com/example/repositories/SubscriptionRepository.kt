package com.example.repositories

import com.example.models.Subscription
import com.example.repositories.SubscriptionRepository.subscription.clientId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object SubscriptionRepository {
    private object subscription : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val type = varchar("type", 100)
        val initialDate =  varchar("initial_date", 100)
        val endDate = varchar("end_date", 100)
    }

    private fun resultRowToSubscription(row: ResultRow): Subscription {
        return Subscription(
            id = row[subscription.id],
            idClient = row[clientId],
            type = row[subscription.type],
            initialDate = row[subscription.initialDate],
            endDate = row[subscription.endDate]
        )
    }

    // Funcion que obtiene todas las suscripciones
    fun getAllSubscriptions(): List<Subscription> {
        return transaction {
            subscription.selectAll().map { resultRowToSubscription(it) }
        }
    }

    // Funcion que obtiene una suscripcion por su id
    fun getSubscriptionById(subscriptionId: Int): Subscription? {
        return transaction {
            subscription.select { subscription.id eq subscriptionId }.map { resultRowToSubscription(it) }.firstOrNull()
        }
    }

    // Funcion que agrega una suscripcion
    fun addSubscription(subscription: Subscription) {
        transaction {
            SubscriptionRepository.subscription.insert {
                it[clientId] = subscription.idClient
                it[type] = subscription.type
                it[initialDate] = subscription.initialDate
                it[endDate] = subscription.endDate
            }
        }
    }

    // Funcion que actualiza una suscripcion
    fun updateSubscription(subscription: Subscription) {
        transaction {
            SubscriptionRepository.subscription.update({ SubscriptionRepository.subscription.id eq subscription.id }) {
                it[clientId] = subscription.idClient
                it[type] = subscription.type
                it[initialDate] = subscription.initialDate
                it[endDate] = subscription.endDate
            }
        }
    }

    // Funcion que obtiene la suscripcion de un cliente
    fun getSubscriptionByClientId(clientId: Int): List<Subscription> {
        return transaction {
            subscription.select { subscription.clientId eq clientId }.map { resultRowToSubscription(it) }
        }
    }
}
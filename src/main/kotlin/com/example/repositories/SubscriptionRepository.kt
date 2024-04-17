package com.example.repositories

import com.example.models.Subscription

object SubscriptionRepository {
    private val subscriptions = mutableListOf<Subscription>(
        Subscription(1, "Free", "2021-01-01", "2021-12-31", 1),
        Subscription(2, "Premium", "2021-01-01", "2021-12-31", 2),
    )

    fun getSubscriptionsByClientId(clientId: Int): List<Subscription> {
        return subscriptions.filter { it.idClient == clientId }
    }
}
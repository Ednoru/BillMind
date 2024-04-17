package com.example.repositories

import com.example.models.Reminder

object ReminderRepository {
    private val reminders = mutableListOf(
        Reminder(1, "Pagar la renta", "2021-09-30", false, 1),
        Reminder(2, "Pagar la luz", "2021-09-30", false, 2),
        Reminder(3, "Pagar el agua", "2021-09-30", false, 2),
    )

    fun getRemindersByClientId(clientId: Int): List<Reminder> {
        return reminders.filter { it.idClient == clientId }
    }
}
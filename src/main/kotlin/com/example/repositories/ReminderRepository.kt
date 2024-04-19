package com.example.repositories

import com.example.models.Reminder
import com.example.repositories.ReminderRepository.reminder.clientId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ReminderRepository {
    private object reminder : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val message = varchar("name", 100)
        val date = varchar("date", 100)
        val endReminder = varchar("end_reminder", 100)
    }

    private fun resultRowToReminder(row: ResultRow): Reminder {
        return Reminder(
            id = row[reminder.id],
            idClient = row[clientId],
            message = row[reminder.message],
            date = row[reminder.date],
            endReminder = row[reminder.endReminder]
        )
    }

    // Funcion que obtiene todos los recordatorios
    fun getAllReminders(): List<Reminder> {
        return transaction {
            reminder.selectAll().map { resultRowToReminder(it) }
        }
    }

    // Funcion que obtiene un recordatorio por su id
    fun getReminderById(reminderId: Int): Reminder? {
        return transaction {
            reminder.select { reminder.id eq reminderId }.map { resultRowToReminder(it) }.firstOrNull()
        }
    }

    // Funcion que agrega un recordatorio
    fun addReminder(reminder: Reminder) {
        transaction {
            ReminderRepository.reminder.insert {
                it[clientId] = reminder.idClient
                it[message] = reminder.message
                it[date] = reminder.date
                it[endReminder] = reminder.endReminder
            }
        }
    }

    // Funcion que actualiza un recordatorio
    fun updateReminder(reminder: Reminder) {
        transaction {
            ReminderRepository.reminder.update({ ReminderRepository.reminder.id eq reminder.id }) {
                it[clientId] = reminder.idClient
                it[message] = reminder.message
                it[date] = reminder.date
                it[endReminder] = reminder.endReminder
            }
        }
    }

    // Funcion que obtiene todos los recordatorios de un cliente
    fun getReminderByClientId(clientId: Int): List<Reminder> {
        return transaction {
            reminder.select { reminder.clientId eq clientId }.map { resultRowToReminder(it) }
        }
    }
}
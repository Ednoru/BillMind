package com.example.repositories

import com.example.models.Debt
import com.example.repositories.DebtRepository.debts.clientId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DebtRepository {
    private object debts : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val name = varchar("name", 100)
        val expiration =  varchar("expiration", 100)
        val amount = varchar("amount", 100)
        val description = varchar("description", 100)
        val relevance = varchar("relevance", 100)
    }

    private fun resultRowToDebt(row: ResultRow): Debt {
        return Debt(
            id = row[debts.id],
            idClient = row[clientId],
            name = row[debts.name],
            expiration = row[debts.expiration],
            amount = row[debts.amount],
            description = row[debts.description],
            relevance = row[debts.relevance]
        )
    }

    // Funcion que obtiene todas las deudas
    fun getAllDebts(): List<Debt> {
        return transaction {
            debts.selectAll().map { resultRowToDebt(it) }
        }
    }

    // Funcion que obtiene una deuda por su id
    fun getDebtById(debtId: Int): Debt? {
        return transaction {
            debts.select { debts.id eq debtId }.map { resultRowToDebt(it) }.firstOrNull()
        }
    }

    // Funcion que agrega una deuda
    fun addDebt(debt: Debt) {
        transaction {
            DebtRepository.debts.insert {
                it[clientId] = debt.idClient
                it[name] = debt.name
                it[expiration] = debt.expiration
                it[amount] = debt.amount
                it[description] = debt.description
                it[relevance] = debt.relevance
            }
        }
    }

    // Funcion que actualiza una deuda
    fun updateDebt(debt: Debt) {
        transaction {
            DebtRepository.debts.update({ DebtRepository.debts.id eq debt.id }) {
                it[clientId] = debt.idClient
                it[name] = debt.name
                it[expiration] = debt.expiration
                it[amount] = debt.amount
                it[description] = debt.description
                it[relevance] = debt.relevance
            }
        }
    }

    // Funcion que obtiene todas las deudas de un cliente
    fun getDebtsByClientId(clientId: Int): List<Debt> {
        return transaction {
            debts.select { debts.clientId eq clientId }.map { resultRowToDebt(it) }
        }
    }
}

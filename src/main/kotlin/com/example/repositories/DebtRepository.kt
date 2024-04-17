package com.example.repositories

import com.example.models.Debt
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

object DebtRepository {
    private val debts = mutableListOf<Debt>(
        Debt(1, "Tarjeta de crédito", "2024-05-10", 150.25, "Pago mínimo", FALSE, 1),
        Debt(2, "Préstamo personal", "2024-06-15", 500.00, "Cuota mensual", TRUE, 2),
        Debt(3, "Factura de luz", "2024-04-20", 75.50, "Consumo del mes", FALSE, 2),
    )

    fun getDebtsByClientId(clientId: Int): List<Debt> {
        return debts.filter { it.idClient == clientId }
    }

    fun updateDebt(clientId: Int, debtId: Int, updatedDebt: Debt) {
        val index = debts.indexOfFirst { it.id == debtId }
        if (index != -1) {
            debts[index] = updatedDebt.copy(id = debtId, idClient = clientId)
        }
    }
}
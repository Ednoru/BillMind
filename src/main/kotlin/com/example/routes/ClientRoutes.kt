package com.example.routes

import com.example.models.Client
import com.example.models.Debt
import com.example.repositories.DebtRepository
import com.example.repositories.ReminderRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val clients = mutableListOf(
    Client(1, "Juan", "Perez", "jp@gmail.com", "1234567890"),
    Client(2, "Maria", "Lopez", "ml@gmail.com", "0987654321"),
)

fun Route.clientRouting() {
    route("/client") {
        get {
            if(clients.isNotEmpty()) {
                call.respond(clients)
            } else {
                call.respondText("No clients found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val client = clients.find { it.id == id.toInt() } ?: return@get call.respondText(
                "No client with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(client)
        }

        post {
            val client = call.receive<Client>()
            clients.add(client)
            call.respondText("Client stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Id no encontrado",
                status = HttpStatusCode.BadRequest
            )
            if(clients.removeIf { it.id == id.toInt() }) {
                call.respondText("Cliente removido correctamente", status = HttpStatusCode.OK)
            } else {
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }

        }
    }
}

fun Route.clientDebtsRouting() {
    route("/client/{clientId}/debts") {
        // Muestra las deudas de un cliente
        get {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respondText(
                    "Cliente ID no válido",
                    status = HttpStatusCode.BadRequest
                )
                return@get
            }
            val clientDebts = DebtRepository.getDebtsByClientId(clientId)
            if (clientDebts.isNotEmpty()) {
                call.respond(clientDebts)
            } else {
                call.respondText(
                    "No se encontraron deudas para el cliente con ID $clientId",
                    status = HttpStatusCode.NotFound
                )
            }
        }
        // Cambia algun detalle de la deuda del cliente
        put("{debtId}") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            val debtId = call.parameters["debtId"]?.toIntOrNull()
            if (clientId == null || debtId == null) {
                call.respondText(
                    "Cliente ID o Deuda ID no válido",
                    status = HttpStatusCode.BadRequest
                )
                return@put
            }
            val debt = DebtRepository.getDebtsByClientId(clientId).find { it.id == debtId }
            if (debt == null) {
                call.respondText(
                    "No se encontró la deuda con ID $debtId para el cliente con ID $clientId",
                    status = HttpStatusCode.NotFound
                )
                return@put
            }
            val updatedDebt = call.receive<Debt>()
            DebtRepository.updateDebt(clientId, debtId, updatedDebt)
            call.respondText("Deuda actualizada correctamente", status = HttpStatusCode.OK)
        }
    }
}

// Funcion que maneja las rutas de los recordatorios de un cliente
fun Route.clientRemindersRouting() {
    route("/client/{clientId}/reminders") {
        // Muestra los recordatorios de un cliente
        get {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respondText(
                    "Cliente ID no válido",
                    status = HttpStatusCode.BadRequest
                )
                return@get
            }
            val clientReminders = ReminderRepository.getRemindersByClientId(clientId)
            if (clientReminders.isNotEmpty()) {
                call.respond(clientReminders)
            } else {
                call.respondText(
                    "No se encontraron recordatorios para el cliente con ID $clientId",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}
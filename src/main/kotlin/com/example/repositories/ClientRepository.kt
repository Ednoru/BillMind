package com.example.repositories

import com.example.models.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ClientRepository {
    private object client : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 100)
        val lastName = varchar("last_name", 100)
        val mail = varchar("mail", 100)
        val phone = varchar("phone", 100)
    }

    private fun resultRowToClient(row: ResultRow): Client {
        return Client(
            id = row[client.id],
            name = row[client.name],
            lastName = row[client.lastName],
            mail = row[client.mail],
            phone = row[client.phone]
        )
    }

    // Funcion para obtener todos los clientes
    fun getAllClients(): List<Client> {
        return transaction {
            client.selectAll().map { resultRowToClient(it) }
        }
    }

    // Funcion para obtener un cliente por su id
    fun getClientById(clientId: Int): Client? {
        return transaction {
            client.select { client.id eq clientId }.map { resultRowToClient(it) }.firstOrNull()
        }
    }

    // Funcion para agregar un cliente
    fun addClient(client: Client) {
        transaction {
            ClientRepository.client.insert {
                it[name] = client.name
                it[lastName] = client.lastName
                it[mail] = client.mail
                it[phone] = client.phone
            }
        }
    }

    // Funcion para actualizar un cliente
    fun updateClient(client: Client) {
        transaction {
            ClientRepository.client.update({ ClientRepository.client.id eq client.id }) {
                it[name] = client.name
                it[lastName] = client.lastName
                it[mail] = client.mail
                it[phone] = client.phone
            }
        }
    }

    // Funcion para eliminar un cliente por su id
    /*fun deleteClientById(clientId: Int) {
        transaction {
            Clients.deleteWhere { Clients.id eq clientId }
        }
    }*/
}
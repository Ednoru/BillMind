package com.example.repositories

import com.example.models.Client

object ClientRepository {
    private val clients = mutableListOf(
        Client(1, "Juan", "Perez", "jp@gmail.com", "1234567890"),
        Client(2, "Maria", "Lopez", "ml@gmail.com", "0987654321"),
    )

    fun addClient(client: Client) {
        clients.add(client)
    }

    fun getClient(id: Int): Client? {
        return clients.find { it.id == id }
    }

    fun getClients(): List<Client> {
        return clients
    }

    fun updateClient(id: Int, client: Client) {
        val index = clients.indexOfFirst { it.id == id }
        if (index != -1) {
            clients[index] = client
        }
    }

    fun deleteClient(id: Int) {
        clients.removeIf { it.id == id }
    }
}
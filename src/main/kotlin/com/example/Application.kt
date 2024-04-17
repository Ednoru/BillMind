package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "127.0.0.1",
        module = Application::module)
        .start(wait = true)

    // Configuración de la conexión a la base de datos
    /*val config = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/nombre_basedatos"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = "usuario"
        password = "contraseña"
        maximumPoolSize = 10
    }

    // Inicialización de la conexión a la base de datos
    Database.connect(HikariDataSource(config))

    */
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureRouting()
}

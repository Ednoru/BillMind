package com.example.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/billmind",
        user = "Noru",
        password = "E_noru1108",
        driver = "org.h2.Driver"
    )
}
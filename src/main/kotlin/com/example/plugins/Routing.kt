package com.example.plugins

import com.example.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Que miras prro?                                                                                                                                                          pto el que lo lea")
        }
        clientRouting()
        clientDebtsRouting()
        clientRemindersRouting()
        clientCardsRouting()
        clientSubscriptionsRouting()
    }
}
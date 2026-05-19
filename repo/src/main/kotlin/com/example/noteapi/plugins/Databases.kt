package com.example.noteapi.plugins

import com.example.noteapi.services.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabase() {
    DatabaseFactory.init()
}

package com.example.noteapi

import com.example.noteapi.plugins.*
import com.example.noteapi.routes.authRoutes
import com.example.noteapi.routes.noteRoutes
import com.example.noteapi.services.DatabaseFactory
import com.example.noteapi.services.JwtService
import com.example.noteapi.services.NoteService
import com.example.noteapi.services.UserService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init() 

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (cause.message ?: "Internal error")))
        }
    }

    val userService = UserService()
    val noteService = NoteService()
    val jwtService = JwtService(this)

    configureSecurity() 

    routing {
        get("/") {
            call.respondText("NoteFlow API is Online and Secure.")
        }
        authRoutes(userService, jwtService)
        noteRoutes(noteService)
    }
}

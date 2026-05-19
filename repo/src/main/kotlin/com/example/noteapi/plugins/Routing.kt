package com.example.noteapi.plugins

import com.example.noteapi.routes.authRoutes
import com.example.noteapi.routes.noteRoutes
import com.example.noteapi.services.JwtService
import com.example.noteapi.services.NoteService
import com.example.noteapi.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String)

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(cause.localizedMessage ?: "Unknown Error"))
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.localizedMessage ?: "Bad Request"))
        }
    }

    val userService = UserService()
    val noteService = NoteService()
    val jwtService = JwtService(this)

    routing {
        get("/") {
            call.respondText("Welcome to Note API!")
        }
        
        authRoutes(userService, jwtService)
        noteRoutes(noteService)
    }
}

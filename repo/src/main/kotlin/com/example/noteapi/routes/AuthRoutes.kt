package com.example.noteapi.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.noteapi.services.JwtService
import com.example.noteapi.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(val username: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val identifier: String, val password: String) // identifier can be username or email

@Serializable
data class AuthResponse(val token: String)

fun Route.authRoutes(userService: UserService, jwtService: JwtService) {
    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            
            if (request.username.isBlank() || request.email.isBlank() || request.password.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "All fields are required"))
                return@post
            }
            
            // Password Validation
            if (request.password.length < 8 || !request.password.any { it.isDigit() }) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Password must be at least 8 characters and contain a number"))
                return@post
            }

            if (userService.getUserByUsername(request.username) != null) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "Username already taken"))
                return@post
            }

            if (userService.getUserByEmail(request.email) != null) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "Email already registered"))
                return@post
            }
            
            val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())
            val user = userService.createUser(request.username, request.email, hashedPassword)
            
            if (user != null) {
                val token = jwtService.generateToken(user)
                call.respond(HttpStatusCode.Created, AuthResponse(token))
            } else {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Registration failed"))
            }
        }
        
        post("/login") {
            val request = call.receive<LoginRequest>()
            // Try login by email first, then username
            val user = userService.getUserByEmail(request.identifier) ?: userService.getUserByUsername(request.identifier)
            
            if (user == null || !BCrypt.verifyer().verify(request.password.toCharArray(), user.passwordHash).verified) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
                return@post
            }
            
            val token = jwtService.generateToken(user)
            call.respond(HttpStatusCode.OK, AuthResponse(token))
        }
    }
}

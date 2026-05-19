package com.example.noteapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.noteapi.models.User
import io.ktor.server.application.*
import java.util.*

class JwtService(application: Application) {
    private val secret = application.environment.config.property("jwt.secret").getString()
    private val audience = application.environment.config.property("jwt.audience").getString()
    private val domain = application.environment.config.property("jwt.domain").getString()

    fun generateToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(domain)
            .withClaim("id", user.id)
            .withClaim("username", user.username)
            .withClaim("email", user.email) // Added email to token claims
            .withExpiresAt(Date(System.currentTimeMillis() + 86400000)) // 1 day
            .sign(Algorithm.HMAC256(secret))
    }
}

package com.example.noteapi.services

import com.example.noteapi.models.User
import com.example.noteapi.models.Users
import com.example.noteapi.services.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class UserService {
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        username = row[Users.username],
        email = row[Users.email],
        passwordHash = row[Users.passwordHash]
    )

    suspend fun createUser(username: String, email: String, passwordHash: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToUser(it) }
    }

    suspend fun getUserByUsername(username: String): User? = dbQuery {
        Users.select { Users.username eq username }
            .map { resultRowToUser(it) }
            .singleOrNull()
    }

    suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }
            .map { resultRowToUser(it) }
            .singleOrNull()
    }
}

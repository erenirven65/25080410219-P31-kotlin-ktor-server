package com.example.noteapi.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String, // New Field
    val passwordHash: String
)

@Serializable
data class Note(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val category: String,
    val isPublic: Boolean,
    val createdAt: String,
    val authorName: String? = null
)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex() // New Unique Field
    val passwordHash = varchar("password_hash", 100)
    override val primaryKey = PrimaryKey(id)
}

object Notes : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(Users.id)
    val title = varchar("title", 255)
    val content = text("content")
    val category = varchar("category", 50).default("General")
    val isPublic = bool("is_public").default(false)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    override val primaryKey = PrimaryKey(id)
}

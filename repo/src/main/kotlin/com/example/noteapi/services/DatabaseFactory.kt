package com.example.noteapi.services

import com.example.noteapi.models.Notes
import com.example.noteapi.models.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        // Changed db name to notes_v2.db to force schema update on Windows
        val driverClassName = "org.sqlite.JDBC"
        val jdbcURL = "jdbc:sqlite:./notes_v2.db"
        val database = Database.connect(jdbcURL, driverClassName)
        
        transaction(database) {
            SchemaUtils.create(Users, Notes)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

package com.example.noteapi.services

import com.example.noteapi.models.Note
import com.example.noteapi.models.Notes
import com.example.noteapi.models.Users
import com.example.noteapi.services.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

class NoteService {
    private fun resultRowToNote(row: ResultRow, author: String? = null) = Note(
        id = row[Notes.id],
        userId = row[Notes.userId],
        title = row[Notes.title],
        content = row[Notes.content],
        category = row[Notes.category],
        isPublic = row[Notes.isPublic],
        createdAt = row[Notes.createdAt].toString(),
        authorName = author
    )

    suspend fun getAllPublicNotes(): List<Note> = dbQuery {
        (Notes innerJoin Users)
            .select { Notes.isPublic eq true }
            .orderBy(Notes.createdAt to SortOrder.DESC)
            .map { resultRowToNote(it, it[Users.username]) }
    }

    suspend fun getUserNotes(userId: Int): List<Note> = dbQuery {
        Notes.select { Notes.userId eq userId }
            .orderBy(Notes.createdAt to SortOrder.DESC)
            .map { resultRowToNote(it) }
    }

    suspend fun createNote(userId: Int, title: String, content: String, category: String, isPublic: Boolean): Note? = dbQuery {
        val insertStatement = Notes.insert {
            it[Notes.userId] = userId
            it[Notes.title] = title
            it[Notes.content] = content
            it[Notes.category] = category
            it[Notes.isPublic] = isPublic
            it[Notes.createdAt] = LocalDateTime.now()
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToNote(it) }
    }

    suspend fun updateNote(id: Int, userId: Int, title: String, content: String, category: String, isPublic: Boolean): Boolean = dbQuery {
        Notes.update({ (Notes.id eq id) and (Notes.userId eq userId) }) {
            it[Notes.title] = title
            it[Notes.content] = content
            it[Notes.category] = category
            it[Notes.isPublic] = isPublic
        } > 0
    }

    suspend fun searchNotes(query: String, userId: Int): List<Note> = dbQuery {
        Notes.select { 
            (Notes.userId eq userId) and 
            ((Notes.title.lowerCase() like "%${query.lowercase()}%") or 
             (Notes.content.lowerCase() like "%${query.lowercase()}%") or
             (Notes.category.lowerCase() like "%${query.lowercase()}%"))
        }.map { resultRowToNote(it) }
    }

    suspend fun deleteNote(id: Int, userId: Int): Boolean = dbQuery {
        Notes.deleteWhere { (Notes.id eq id) and (Notes.userId eq userId) } > 0
    }

    suspend fun getNoteById(id: Int): Note? = dbQuery {
        (Notes leftJoin Users)
            .select { Notes.id eq id }
            .map { resultRowToNote(it, it[Users.username]) }
            .singleOrNull()
    }
}

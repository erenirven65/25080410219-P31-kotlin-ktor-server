package com.example.noteapi.routes

import com.example.noteapi.services.NoteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val title: String, 
    val content: String, 
    val category: String = "General",
    val isPublic: Boolean = false
)

fun Route.noteRoutes(noteService: NoteService) {
    route("/notes") {
        
        get("/feed") {
            val notes = noteService.getAllPublicNotes()
            call.respond(HttpStatusCode.OK, notes)
        }

        get("/shared/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val note = noteService.getNoteById(id)
            if (note == null || !note.isPublic) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(HttpStatusCode.OK, note)
        }

        authenticate("auth-jwt") {
            // My Notes List
            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                call.respond(HttpStatusCode.OK, noteService.getUserNotes(userId))
            }

            // Search My Notes
            get("/search") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                val query = call.request.queryParameters["q"] ?: ""
                call.respond(HttpStatusCode.OK, noteService.searchNotes(query, userId))
            }

            // Create
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<NoteRequest>()
                val note = noteService.createNote(userId, request.title, request.content, request.category, request.isPublic)
                
                if (note != null) call.respond(HttpStatusCode.Created, note)
                else call.respond(HttpStatusCode.InternalServerError)
            }

            // Update (Explicitly define the ID parameter handling)
            put("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@put call.respond(HttpStatusCode.Unauthorized)
                val noteId = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                
                val request = call.receive<NoteRequest>()
                val success = noteService.updateNote(noteId, userId, request.title, request.content, request.category, request.isPublic)
                
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("status" to "updated"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Note not found or unauthorized"))
                }
            }
            
            // Delete
            delete("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@delete call.respond(HttpStatusCode.Unauthorized)
                val noteId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                
                if (noteService.deleteNote(noteId, userId)) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

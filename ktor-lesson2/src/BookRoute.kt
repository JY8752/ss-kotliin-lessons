package com.example

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.bookRoute() {
    route("/book") {
        @Location("/detail/{bookId}")
        data class BookLocation(val bookId: Long)

        get<BookLocation> { request ->
           val response =  BookResponse(request.bookId, "Kotlin入門", "yamanaka")
            call.respond(response)
        }

        post("/register") {
            val request = call.receive<RegisterRequest>()
            call.respondText("registered. id=${request.id} title=${request.title} author=${request.author}")
        }
    }
}

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String
)

data class RegisterRequest(
    val id: Long,
    val title: String,
    val author: String
)
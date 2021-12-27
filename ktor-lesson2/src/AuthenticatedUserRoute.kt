package com.example

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authenticatedUserRoute() {
    get("/authenticated") {
        val user = call.authentication.principal<UserIdPrincipal>()
        call.respondText("authenticated. id=${user?.name}")
    }
}
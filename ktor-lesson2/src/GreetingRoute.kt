package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.greetingRoute() {
    route("greeting") {
        get("/hello") {
            call.respondText("Hello!!")
        }
        get("/goodmorning") {
            call.respondText("GoodMornong!!")
        }
    }
}
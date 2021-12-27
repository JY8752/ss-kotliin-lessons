package com.example

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Locations)
    install(ContentNegotiation) {
        jackson()
    }
    install(Authentication) {
        basic {
            validate { credentials ->
               if(credentials.name == "user" && credentials.password == "password") {
                   UserIdPrincipal(credentials.name)
               } else {
                   null
               }
            }
        }
    }
    routing {
        get("/") {
            call.respondText("Hello Ktor!!")
        }
        get("/hello/{name}") {
            val name = call.parameters["name"]
            call.respondText("Hello $name!!")
        }
        get("/hello") {
            val name = call.parameters["name"]
            call.respondText("Hello $name!!")
        }
        greetingRoute()
        userRoute()
        bookRoute()
        authenticate {
            authenticatedUserRoute()
        }
    }
}

@Location("/user")
class UserLocation {
    @Location("/{id}")
    data class GetUserLocation(val id: Long)

    @Location("/detail/{id}")
    data class GetDetailLocation(val id: Long)
}

fun Routing.userRoute() {
    get<UserLocation.GetUserLocation> { param ->
        val id = param.id
        call.respondText("id=$id")
    }
    get<UserLocation.GetDetailLocation> { param ->
        val id = param.id
        call.respondText("getDetail id=$id")
    }
}


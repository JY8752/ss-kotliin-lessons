package example.greeter.client

import example.greeter.GreeterGrpcKt
import example.greeter.HelloRequest
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

private const val HOST = "localhost"
private const val PORT = 5051

fun main() = runBlocking{
    //コネクション的なもの。
    val channel = ManagedChannelBuilder.forAddress(HOST, PORT)
        .usePlaintext() //SSLの無効化
        .build()

    try {
        //クライアント
        val stub = GreeterGrpcKt.GreeterCoroutineStub(channel)

        //パラメーター
        val name = "Kotlin"
        val request = HelloRequest.newBuilder().setName(name).build()

        //非同期でリクエスト
        val response = async{ stub.hello(request) }

        println("Response Text: ${response.await().text}")
    } finally {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
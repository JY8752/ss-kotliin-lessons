package example.greeter.server

import io.grpc.ServerBuilder

private const val PORT = 5051

fun main() {
    //gRPCサーバーのビルド
    val server = ServerBuilder
        .forPort(PORT)
        .addService(GreeterService())
        .build()

    server.start()
    println("Started. port: $PORT")

    //アプリケーションが停止するまでリクエストを受け付ける状態に
    server.awaitTermination()
}
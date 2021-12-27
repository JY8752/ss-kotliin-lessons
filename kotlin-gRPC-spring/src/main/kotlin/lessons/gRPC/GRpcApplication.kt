package lessons.gRPC

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GRpcApplication

fun main(args: Array<String>) {
	runApplication<GRpcApplication>(*args)
}

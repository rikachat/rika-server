package chat.rika.rikaserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RikaApplication

fun main(args: Array<String>) {
    runApplication<RikaApplication>(*args)
}

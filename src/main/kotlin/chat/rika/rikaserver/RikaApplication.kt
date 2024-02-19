package chat.rika.rikaserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["chat.rika.rikaserver.services"])
@ComponentScan(basePackages = ["chat.rika.rikaserver.controllers"])
@EnableJpaRepositories(basePackages = ["chat.rika.rikaserver.repositories"])
@EntityScan(basePackages = ["chat.rika.rikaserver.entities"])
@EnableCaching
class RikaApplication

fun main(args: Array<String>) {
    runApplication<RikaApplication>(*args)
}

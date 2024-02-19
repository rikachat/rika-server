package chat.rika.rikaserver.repositories

import chat.rika.rikaserver.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findUserByUsername(username: String): User?
    fun findUserByPhoneNumber(phoneNumber: String): User?
}
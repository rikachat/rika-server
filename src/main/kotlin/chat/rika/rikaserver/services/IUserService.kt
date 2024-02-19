package chat.rika.rikaserver.services

import chat.rika.rikaserver.dto.UserDto
import chat.rika.rikaserver.entities.User

interface IUserService {
    fun getUserByUsername(username: String): UserDto?

    fun getAllUsers(): List<UserDto>

    fun register(user: User): UserDto?

    fun validateLogin(username: String, password: String): Boolean

    fun generateAndStoreToken(username: String): String
}
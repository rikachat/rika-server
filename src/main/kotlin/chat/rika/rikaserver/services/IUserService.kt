package chat.rika.rikaserver.services

import chat.rika.rikaserver.dto.UserDto
import org.springframework.stereotype.Service

@Service
interface IUserService {
    fun getUserByUsername(username: String): UserDto?
}
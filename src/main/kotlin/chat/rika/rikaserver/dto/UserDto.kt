package chat.rika.rikaserver.dto

import chat.rika.rikaserver.entities.User

data class UserDto(val username: String, val role: User.Role)

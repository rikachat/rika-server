package chat.rika.rikaserver.services.impl

import chat.rika.rikaserver.dto.UserDto
import chat.rika.rikaserver.repositories.UserRepository
import chat.rika.rikaserver.services.IUserService

class UserServiceImpl(private val userRepository: UserRepository): IUserService {
    override fun getUserByUsername(username: String): UserDto? =
        userRepository.findUserByUsername(username)?.toDto()
}
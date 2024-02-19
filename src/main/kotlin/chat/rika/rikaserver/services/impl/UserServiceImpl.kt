package chat.rika.rikaserver.services.impl

import chat.rika.rikaserver.dto.UserDto
import chat.rika.rikaserver.entities.User
import chat.rika.rikaserver.repositories.UserRepository
import chat.rika.rikaserver.services.IUserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository): IUserService {
    override fun getUserByUsername(username: String): UserDto? =
        userRepository.findUserByUsername(username)?.toDto()

    override fun getAllUsers(): List<UserDto> = userRepository
        .findAll()
        .map { it.toDto() }
        .toList()

    override fun registerUser(user: User): UserDto? {
        userRepository.findUserByUsername(user.username)?.let {
            return null
        }
        return userRepository.save(user).toDto()
    }
}
package chat.rika.rikaserver.services.impl

import chat.rika.rikaserver.dto.UserDto
import chat.rika.rikaserver.entities.User
import chat.rika.rikaserver.repositories.UserRepository
import chat.rika.rikaserver.services.IUserService
import org.springframework.cache.CacheManager
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val cacheManager: CacheManager
): IUserService {
    override fun getUserByUsername(username: String): UserDto? =
        userRepository.findUserByUsername(username)?.toDto()

    override fun getAllUsers(): List<UserDto> = userRepository
        .findAll()
        .map { it.toDto() }
        .toList()

    override fun register(user: User): UserDto? {
        userRepository.findUserByUsername(user.username)?.let {
            return null
        }
        userRepository.findUserByPhoneNumber(user.username)?.let {
            return null
        }
        return userRepository.save(user).toDto()
    }

    override fun validateLogin(username: String, password: String): Boolean {
        userRepository.findUserByUsername(username)?.let {
            return BCrypt.checkpw(password, it.password)
        }
        return false
    }

    override fun generateAndStoreToken(username: String): String {
        val token = UUID.randomUUID().toString()
        cacheManager.getCache("token")?.put(username, token)
        return token
    }
}
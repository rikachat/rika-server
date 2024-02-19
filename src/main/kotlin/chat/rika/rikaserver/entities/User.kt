package chat.rika.rikaserver.entities

import chat.rika.rikaserver.dto.UserDto
import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue
    val id: Long,
    @Column(unique = true, nullable = false)
    val username: String,
    @Column(unique = true, nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    val role: Role
) {
    fun toDto() = UserDto(username, role)

    enum class Role {
        ADMIN,
        NORMAL,
        PRO
    }
}
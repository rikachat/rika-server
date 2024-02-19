package chat.rika.rikaserver.entities

import chat.rika.rikaserver.dto.UserDto
import jakarta.persistence.*

@Entity
@Table(name = "user_table")
class User(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val phoneNumber: String,

    @Column(nullable = false)
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
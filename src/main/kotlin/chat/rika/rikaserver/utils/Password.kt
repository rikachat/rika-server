package chat.rika.rikaserver.utils

import org.springframework.security.crypto.bcrypt.BCrypt

fun String.hash(): String {
    return BCrypt.hashpw(this, BCrypt.gensalt())
}
package chat.rika.rikaserver.forms

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterUserForm(
    @field:NotBlank(message = "user.username.blank")
    @field:Size(min = 3, max = 20, message = "user.username.length")
    val username: String,

    @field:NotBlank(message = "user.password.blank")
    @field:Size(min = 3, max = 20, message = "user.password.length")
    val password: String
)

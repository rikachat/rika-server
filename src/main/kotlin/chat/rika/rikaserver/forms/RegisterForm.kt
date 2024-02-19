package chat.rika.rikaserver.forms

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

data class RegisterForm(
    @field:NotBlank(message = "user.username.blank")
    @field:Size(min = 3, max = 20, message = "user.username.length")
    val username: String,

    @field:NotBlank(message = "user.password.blank")
    @field:Size(min = 3, max = 20, message = "user.password.length")
    val password: String,

    @field:NotBlank(message = "user.phoneNumber.blank")
    @field:Length(max = 11, min = 11, message = "user.phoneNumber.length")
    val phoneNumber: String,

    @field:NotBlank(message = "user.captcha.blank")
    val captcha: String,
)

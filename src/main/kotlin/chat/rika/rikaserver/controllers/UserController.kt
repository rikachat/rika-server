package chat.rika.rikaserver.controllers

import chat.rika.rikaserver.entities.User
import chat.rika.rikaserver.forms.LoginForm
import chat.rika.rikaserver.forms.RegisterForm
import chat.rika.rikaserver.services.IUserService
import chat.rika.rikaserver.services.impl.PhoneNumberCaptchaService
import chat.rika.rikaserver.utils.hash
import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: IUserService,
    private val captchaService: PhoneNumberCaptchaService,
    private val validator: Validator
) {
    @GetMapping("/user/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<Any> {
        userService.getUserByUsername(username)?.let {
            return ResponseEntity.ok(it)
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/users")
    fun getAllUsers() = userService.getAllUsers()

    @PostMapping("/user/register")
    fun register(@RequestBody form: RegisterForm): ResponseEntity<Any> {
        val result = validator.validate(form)
        if (result.isNotEmpty())
            return ResponseEntity.badRequest().body(result.map { it.message })

        if (captchaService.getCaptchaByDestination(form.phoneNumber) == null) {
            return ResponseEntity.badRequest().build()
        }

        if (captchaService.getCaptchaByDestination(form.phoneNumber) != form.captcha) {
            return ResponseEntity.badRequest().build()
        }

        userService.register(
            User(
                username = form.username,
                password = form.password.hash(),
                phoneNumber = form.phoneNumber,
                role = User.Role.NORMAL
            )
        )?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    @PostMapping("/user/login")
    fun login(@RequestBody form: LoginForm): ResponseEntity<Any> {
        val result = validator.validate(form)
        if (result.isNotEmpty())
            return ResponseEntity.badRequest().body(result.map { it.message })

        if (!userService.validateLogin(form.username, form.password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(mapOf("token" to
                userService.generateAndStoreToken(form.username)))
    }

    @GetMapping("/user/captcha/{phoneNumber}")
    fun sendCaptcha(@PathVariable phoneNumber: String): ResponseEntity<Any> {
        return when (captchaService.sendCaptcha(phoneNumber)) {
            true -> ResponseEntity.ok().build()
            false -> ResponseEntity.badRequest().build()
        }
    }
}
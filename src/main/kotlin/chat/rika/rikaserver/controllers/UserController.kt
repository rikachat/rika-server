package chat.rika.rikaserver.controllers

import chat.rika.rikaserver.entities.User
import chat.rika.rikaserver.forms.RegisterUserForm
import chat.rika.rikaserver.services.IUserService
import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: IUserService,
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

    @PostMapping("/user")
    fun registerUser(@RequestBody form: RegisterUserForm): ResponseEntity<Any> {
        val result = validator.validate(form)
        if (result.isNotEmpty())
            return ResponseEntity.badRequest().body(result.map { it.message })

        userService.registerUser(
            User(
                username = form.username,
                password = form.password,
                role = User.Role.NORMAL
            )
        )?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }
}
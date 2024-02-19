package chat.rika.rikaserver.controllers

import chat.rika.rikaserver.services.IUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: IUserService) {
    @GetMapping("/user/{username}")
    fun getUserByUsername(@PathVariable username: String) =
        userService.getUserByUsername(username)
}
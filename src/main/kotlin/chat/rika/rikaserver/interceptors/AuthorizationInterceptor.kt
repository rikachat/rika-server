package chat.rika.rikaserver.interceptors

import chat.rika.rikaserver.entities.User
import chat.rika.rikaserver.services.IUserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.cache.CacheManager
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Order(Ordered.HIGHEST_PRECEDENCE)
annotation class Role(val role: User.Role)

class AuthorizationInterceptor(
    private val userService: IUserService,
    private val cacheManager: CacheManager
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }
        if (!handler.method.isAnnotationPresent(Role::class.java)) {
            return true
        }

        val rawToken = request.getHeader("Authorization")
        if (rawToken.isEmpty()) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }
        val splitToken = rawToken.split(":")
        if (splitToken.isEmpty()) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }
        val username = splitToken[0]
        val token = splitToken[1]
        val realToken = cacheManager.getCache("token")?.get(username)
        if (realToken == null) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }
        if (realToken.get() != token) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }

        val requiredRole = handler.method.getAnnotation(Role::class.java).role
        userService.getUserByUsername(username)?.let {
            if (it.role.priority <= requiredRole.priority) {
                return true
            }
            response.sendError(HttpStatus.UNAUTHORIZED.value())
            return false
        }

        response.status = HttpStatus.UNAUTHORIZED.value()
        return false
    }
}
package chat.rika.rikaserver.services

interface ICaptchaService {
    fun sendCaptcha(destination: String): Boolean

    fun getCaptchaByDestination(destination: String): String?
}
package chat.rika.rikaserver.services.impl

import chat.rika.rikaserver.services.ICaptchaService
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.util.*

@Service
class PhoneNumberCaptchaService(private val cacheManager: CacheManager): ICaptchaService {
    override fun sendCaptcha(destination: String): Boolean {
        val random = Random()
        val captcha = random.nextInt(999999 - 100000 + 1) + 100000
        // TODO Send captcha to phone number
        cacheManager
            .getCache("captcha")
            ?.put(destination, captcha.toString())
        println(cacheManager
            .getCache("captcha")
            ?.get(destination))
        return true
    }

    override fun getCaptchaByDestination(destination: String): String? {
        return cacheManager
            .getCache("captcha")
            ?.get(destination, String::class.java)
    }
}
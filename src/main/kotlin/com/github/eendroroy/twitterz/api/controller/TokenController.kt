package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.security.TokenGenerator
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = ["token"])
class TokenController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var tokenGenerator: TokenGenerator

    @RequestMapping(
        path = ["create"], method = [RequestMethod.POST],
        consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun register(@RequestBody body: Map<String, String>,response: HttpServletResponse): Map<String, Any> {
        val email: String = body["email"]!!
        val password: String = body["password"]!!
        val user: User? = userService.findUserByEmail(email)
        if (user == null) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return mutableMapOf("success" to true, "details" to "user not found")
        }
        if (passwordEncoder.match(password, user.password!!)) {
            val token: String = tokenGenerator.token()!!
            user.accessToken = token
            userService.saveUser(user)
            return mutableMapOf("success" to true, "token" to token)
        }
        response.status = HttpStatus.UNAUTHORIZED.value()
        return mutableMapOf("success" to false, "details" to "password did not match")
    }
}

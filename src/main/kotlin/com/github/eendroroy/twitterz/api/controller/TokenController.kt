package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.resource.TokenResource
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.security.TokenGenerator
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(
        path = ["token"],
        consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class TokenController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var tokenGenerator: TokenGenerator

    @PostMapping("create")
    @ResponseBody
    fun create(@RequestBody body: Map<String, String>): ResponseEntity<Resource<TokenResource>> {
        val email: String = body["email"]!!
        val password: String = body["password"]!!
        val user: User? = userService.findUserByEmail(email)
        if (user == null) {
            return ResponseEntity(Resource(TokenResource("user not found", 0)), HttpStatus.UNPROCESSABLE_ENTITY)
        } else if (passwordEncoder.match(password, user.password!!)) {
            val token: String = tokenGenerator.token()!!
            user.accessToken = token
            userService.saveUser(user)
            return ResponseEntity.ok(Resource(TokenResource(token)))
        }

        return ResponseEntity(Resource(TokenResource("password did not match", 0)), HttpStatus.UNPROCESSABLE_ENTITY)
    }
}

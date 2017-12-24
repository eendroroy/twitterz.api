package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.security.TokenGenerator
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import java.text.ParseException

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = '/token')
class TokenController {
    @Autowired
    private UserService userService

    @Qualifier('encoder')
    @Autowired
    private PasswordEncoder.Encoder passwordEncoder

    @Qualifier('generator')
    @Autowired
    private TokenGenerator.Generator tokenGenerator

    @RequestMapping(
            path = 'create', method = RequestMethod.POST,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    Map<Object, Object> register(@RequestBody Map body) throws ParseException {
        String email = body.email
        String password = body.password
        User user = userService.findUserByEmail(email)
        if (user == null) { return [success:false, details:'user not found',] }
        if (passwordEncoder.match(password, user.password)) {
            String token = tokenGenerator.token()
            user.password = password
            user.accessToken = token
            userService.saveUser(user)
            return [token:token]
        }
        [success:false, details:'password did not match',]
    }
}

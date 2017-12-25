package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.security.TokenGenerator
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse
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

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private TokenGenerator tokenGenerator

    @RequestMapping(
            path = 'create', method = RequestMethod.POST,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    Map<Object, Object> register(@RequestBody Map body, HttpServletResponse response) throws ParseException {
        String email = body.email
        String password = body.password
        User user = userService.findUserByEmail(email)
        if (user == null) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return [success:false, details:'user not found',]
        }
        if (passwordEncoder.match(password, user.password)) {
            String token = tokenGenerator.token()
            user.accessToken = token
            userService.saveUser(user)
            return [success:true, token:token,]
        }
        response.status = HttpStatus.UNAUTHORIZED.value()
        [success:false, details:'password did not match',]
    }
}

package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
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
@RequestMapping(path = '/user')
class UserController {
    @Autowired
    private UserService userService

    @Autowired
    private PasswordEncoder passwordEncoder

    @RequestMapping(
            path = 'register', method = RequestMethod.POST,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    Map<Object, Object> register(@RequestBody User user, HttpServletResponse response) throws ParseException {
        try {
            user.password = passwordEncoder.encode(user.password)
            user.active = 1
            userService.saveUser(user)
            [success:true, _embedded:[user:user],]
        } catch (DataIntegrityViolationException exception) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            [success:false, details:exception.message, _embedded:[user:user],]
        }
    }
}

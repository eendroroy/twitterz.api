package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
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

    @RequestMapping(
            path = 'register', method = RequestMethod.POST,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    Map<Object, Object> register(
            @RequestBody User user, HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        try {
            userService.saveUser(user)
            user.password = '****'
            [_embedded: [user: user], success: false] as Map<Object, Object>
        } catch (DataIntegrityViolationException exception){
            exception.printStackTrace()
            response.status = 400
            [success: false, details: exception.message] as Map<Object, Object>
        }
    }
}

package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = "/user")
class UserController {
    @Autowired
    transient private UserService userService

    @RequestMapping(path = "register", method = RequestMethod.POST)
    @ResponseBody
    long register(
            HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        String userName = request.getParameter "userName"
        String email = request.getParameter "email"
        String password = request.getParameter "password"
        String date = request.getParameter "date_of_birth"
        Date date_of_birth = null
        if( date != null) {
            date_of_birth = new SimpleDateFormat("dd/MM/yyyy").parse(date)
        }

        User user = new User()
        user.setEmail(email)
        user.setUserName(userName)
        user.setPassword(password)
        user.setDateOfBirth(date_of_birth)

        return userService.saveUser(user).getId()
    }
}
package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(
        path = ["/user"],
        consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @PostMapping("register")
    @ResponseBody
    fun register(@RequestBody user: User, response: HttpServletResponse): Map<String, Any?> {
        return try {
            user.password = passwordEncoder.encode(user.password!!)
            user.active = 1
            userService.saveUser(user)
            mapOf("success" to true, "_embedded" to mapOf("user" to user))
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            mapOf("success" to false, "details" to exception.message, "_embedded" to mapOf("user" to user))
        }
    }

    @PostMapping("follow/{userId}")
    @ResponseBody
    fun follow(
            @PathVariable("userId") userId: Long,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): Map<String, Any?> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            val followUser: User? = userService.findUserById(userId)

            when {
                followUser == null -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    return mapOf(
                            "Success" to false,
                            "details" to "can not find user with id {$userId}.",
                            "_embedded" to mapOf<Any, Any?>("follow" to userId)
                    )
                }
                user.id == followUser.id -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    return mapOf(
                            "Success" to false,
                            "details" to "can not follow self.",
                            "_embedded" to mapOf<Any, Any?>("follow" to followUser)
                    )
                }
                user.followings.contains(followUser) -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    return mapOf(
                            "Success" to false,
                            "details" to "user {$userId} is already being followed.",
                            "_embedded" to mapOf<Any, Any?>("follow" to followUser)
                    )
                }
            }
            user.followings = user.followings.plus(followUser)
            userService.saveUser(user)
            mapOf("Success" to true, "_embedded" to mapOf<Any, Any?>("follow" to user.followings))
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            mapOf(
                    "success" to false,
                    "details" to exception.message,
                    "_embedded" to mapOf<Any, Any?>("follow" to userId)
            )
        }
    }

    @GetMapping("followings")
    @ResponseBody
    fun followings(request: HttpServletRequest, response: HttpServletResponse): Map<String, Any?> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            mapOf("Success" to true, "_embedded" to mapOf<Any, Any?>("followings" to user.followings))
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            mapOf("success" to false, "details" to exception.message)
        }
    }

    @GetMapping("followers")
    @ResponseBody
    fun followers(request: HttpServletRequest, response: HttpServletResponse): Map<String, Any?> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            mapOf("Success" to true, "_embedded" to mapOf<Any, Any?>("followers" to user.followers))
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            mapOf("success" to false, "details" to exception.message)
        }
    }
}

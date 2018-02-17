package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.resource.UserResource
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    fun register(@RequestBody user: User, response: HttpServletResponse): ResponseEntity<Resource<UserResource>> {
        return try {
            user.password = passwordEncoder.encode(user.password!!)
            user.active = 1
            userService.saveUser(user)
            val resource: Resource<UserResource> = Resource(UserResource(user))
            ok(resource)
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            throw Exception()
        }
    }

    @PostMapping("follow/{userId}")
    @ResponseBody
    fun follow(
            @PathVariable("userId") userId: Long,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ResponseEntity<Resources<UserResource>> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            val followUser: User? = userService.findUserById(userId)

            when {
                followUser == null -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    throw Exception("can not find user with id {$userId}.")
                }
                user.id == followUser.id -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    throw Exception("can not follow self.")
                }
                user.followings.contains(followUser) -> {
                    response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
                    throw Exception("user {$userId} is already being followed.")
                }
            }
            user.followings = user.followings.plus(followUser)
            userService.saveUser(user)
            val resources: Resources<UserResource> = Resources(user.followings.map { UserResource(it!!) })
            ok(resources)
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            throw Exception()
        }
    }

    @GetMapping("followings")
    @ResponseBody
    fun followings(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Resources<UserResource>> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            val resources: Resources<UserResource> = Resources(user.followings.map { UserResource(it!!) })
            ok(resources)
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            throw Exception()
        }
    }

    @GetMapping("followers")
    @ResponseBody
    fun followers(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Resources<UserResource>> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            val resources: Resources<UserResource> = Resources(user.followers.map { UserResource(it!!) })
            ok(resources)
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            throw Exception()
        }
    }
}

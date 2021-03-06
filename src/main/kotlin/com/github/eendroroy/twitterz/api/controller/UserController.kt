package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.exceptions.AlreadyFollowingException
import com.github.eendroroy.twitterz.api.exceptions.EmailAlreadyExistsException
import com.github.eendroroy.twitterz.api.exceptions.FollowSelfException
import com.github.eendroroy.twitterz.api.exceptions.UserNameAlreadyExistsException
import com.github.eendroroy.twitterz.api.exceptions.UserNotFoundException
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
        path = ["users"],
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
    fun register(@RequestBody userResource: UserResource): ResponseEntity<Resource<UserResource>> {
        val user = userResource.user!!
        return try {
            when {
                userService.findUserByEmail(user.email!!) != null -> throw EmailAlreadyExistsException()
                userService.findUserByUserName(user.userName!!) != null -> throw UserNameAlreadyExistsException()
                else -> {
                    user.password = passwordEncoder.encode(user.password!!)
                    user.active = 1
                    userService.saveUser(user)
                    ok(Resource(UserResource(user)))
                }
            }
        } catch (exception: DataIntegrityViolationException) {
            ResponseEntity(Resource(UserResource(user, exception.message)), HttpStatus.NOT_ACCEPTABLE)
        } catch (exception: NullPointerException) {
            ResponseEntity(Resource(UserResource(user, exception.message)), HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    @PostMapping("{userId}/follow")
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
                followUser == null -> throw UserNotFoundException()
                user.id == followUser.id -> throw FollowSelfException()
                user.followings.contains(followUser) -> throw AlreadyFollowingException()
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

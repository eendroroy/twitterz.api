package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.controller.TokenController
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

/**
 *
 * @author indrajit
 */

class TokenResource() : ResourceSupport() {
    var token: String? = null
    var success: Boolean? = false
    var message: String? = null

    constructor(token: String?, message: String?, success: Boolean?) : this() {
        this.token = token
        this.message = message
        this.success = success
        this.add(linkTo(methodOn(TokenController::class.java).create(mapOf("test" to "test"))).withSelfRel())
    }

    constructor(token: String?) : this(token, null, true)

    constructor(message: String?, flag: Int?) : this(null, message, false)
}
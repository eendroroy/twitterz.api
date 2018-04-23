package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.controller.UserController
import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

/**
 *
 * @author indrajit
 */

@Relation(collectionRelation = "users")
class UserResource() : ResourceSupport() {

    var user: User? = null
    var message: String? = null

    constructor(user: User?, message: String?) : this() {
        this.user = user
        this.message = message
        this.add(ControllerLinkBuilder.linkTo(methodOn(UserController::class.java).register(this)).withRel("register"))
    }

    constructor(user: User?) : this(user, null)

//    constructor(userResource: UserResource?) : this(userResource!!.user, null)
}
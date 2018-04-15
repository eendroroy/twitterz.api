package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

/**
 *
 * @author indrajit
 */

@Relation(collectionRelation = "users")
class UserResource() : ResourceSupport() {

    var user: User? = null
    var message: String? = null

    constructor(user: User?) : this() {
        this.user = user
    }

    constructor(user: User?, message: String?) : this() {
        this.user = user
        this.message = message
    }

    constructor(userResource: UserResource?) : this() {
        this.user = userResource!!.user
    }

}
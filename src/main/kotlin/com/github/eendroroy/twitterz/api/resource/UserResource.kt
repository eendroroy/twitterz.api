package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

/**
 *
 * @author indrajit
 */

@Relation(collectionRelation = "users")
class UserResource(user: User) : ResourceSupport() {

    var user: User? = null

    init {
        this.user = user
    }

}
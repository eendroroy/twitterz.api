package com.github.eendroroy.twitterz.api.resource

import org.springframework.hateoas.ResourceSupport

class TokenResource() : ResourceSupport() {
    var token: String? = null
    var success: Boolean? = false
    var message: String? = null

    constructor(token: String?, message: String?, success: Boolean?) : this() {
        this.token = token
        this.message = message
        this.success = success
    }

    constructor(token: String?) : this(token, null, true)

    constructor(message: String?, flag: Int?) : this(null, message, false)
}
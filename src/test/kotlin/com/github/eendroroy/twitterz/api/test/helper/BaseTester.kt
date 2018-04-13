package com.github.eendroroy.twitterz.api.test.helper

import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

open class BaseTester {
    protected fun getHttpEntity(body: Any): HttpEntity<Any> {
        val headers = HttpHeaders()
        headers.contentType = MediaTypes.HAL_JSON
        return HttpEntity(body, headers)
    }
}
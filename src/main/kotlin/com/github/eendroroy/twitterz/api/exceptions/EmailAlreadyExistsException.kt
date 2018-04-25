package com.github.eendroroy.twitterz.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "email address already exists.")
class EmailAlreadyExistsException : RuntimeException()

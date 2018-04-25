package com.github.eendroroy.twitterz.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "already following.")
class AlreadyFollowingException : RuntimeException()

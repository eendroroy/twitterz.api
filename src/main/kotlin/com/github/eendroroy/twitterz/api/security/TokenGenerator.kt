package com.github.eendroroy.twitterz.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Configuration

/**
 *
 * @author indrajit
 */

@Configuration
class TokenGenerator {
    fun token(): String? {
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        return Jwts.builder().setSubject("twitter.z.api").signWith(key).compact()
    }
}
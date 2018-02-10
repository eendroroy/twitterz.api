package com.github.eendroroy.twitterz.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import org.springframework.context.annotation.Configuration

import java.security.Key

/**
 *
 * @author indrajit
 */

@Configuration
class TokenGenerator {
    companion object {
        fun token(): String? {
            val key = MacProvider.generateKey()
            return Jwts.builder().setSubject("twitter.z.api").signWith(SignatureAlgorithm.HS512, key).compact()
        }
    }
}
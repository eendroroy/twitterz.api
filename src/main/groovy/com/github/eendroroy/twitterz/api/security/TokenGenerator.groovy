package com.github.eendroroy.twitterz.api.security

import org.springframework.context.annotation.Bean

import java.security.SecureRandom

/**
 * @author indrajit
 */

class TokenGenerator {

    @Bean('generator')
    Generator generator() {
        new Generator()
    }

    class Generator {
        String token() {
            SecureRandom sr = new SecureRandom()
            byte[] bytes = new byte[16]
            sr.nextBytes(bytes)
            new String(bytes)
        }
    }

}

package com.github.eendroroy.twitterz.api.security

import org.apache.commons.codec.digest.Crypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author indrajit
 */

@Configuration
class PasswordEncoder {

    @Bean('encoder')
    Encoder encoder() {
        new Encoder()
    }

    class Encoder {
        String encode(String password) {
            Crypt.crypt(password)
        }
    }
}

package com.github.eendroroy.twitterz.api.security

import org.mindrot.jbcrypt.BCrypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
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
            BCrypt.hashpw(password, BCrypt.gensalt())
        }

        boolean match(String plainPassword, String hashedPassword) {
            BCrypt.checkpw(plainPassword, hashedPassword)
        }
    }
}

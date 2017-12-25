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
    static String encode(String password) {
        BCrypt.hashpw(password, BCrypt.gensalt())
    }

    static boolean match(String plainPassword, String hashedPassword) {
        BCrypt.checkpw(plainPassword, hashedPassword)
    }
}

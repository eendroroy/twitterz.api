package com.github.eendroroy.twitterz.api.security

import org.mindrot.jbcrypt.BCrypt
import org.springframework.context.annotation.Configuration

/**
 *
 * @author indrajit
 */

@Configuration
class PasswordEncoder{
    companion object {
        fun encode(password: String): String? {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }

        fun match(plainPassword: String, hashedPassword: String): Boolean {
            return BCrypt.checkpw(plainPassword, hashedPassword)
        }
    }
}

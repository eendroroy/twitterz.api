package com.github.eendroroy.twitterz.api.config

import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.security.TokenGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author indrajit
 */

@Configuration
class Security {
    @Bean("passwordEncoder")
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoder()
    }

    @Bean("tokenGenerator")
    fun tokenGenerator(): TokenGenerator {
        return TokenGenerator()
    }
}
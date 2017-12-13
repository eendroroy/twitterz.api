package com.github.eendroroy.twitterz.api.security

import org.springframework.context.annotation.Bean

import java.security.SecureRandom

/**
 * @author indrajit
 */
public class TokenGenerator {

    @Bean("generator")
    Generator generator() {
        return new Generator()
    }

    public class Generator{
        public String token(){
            SecureRandom sr = new SecureRandom()
            byte[] bytes = new byte[16]
            sr.nextBytes(bytes)
            return new String(bytes)
        }
    }

}

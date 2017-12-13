package com.github.eendroroy.twitterz.api.security

import org.apache.commons.codec.digest.Crypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author indrajit
 */

@Configuration
class PasswordEncoder {

    @Bean("encoder")
    Encoder encoder(){
        return new Encoder()
    }

    public class Encoder {
        public String encode(String password){
            return Crypt.crypt(password)
        }
    }
}
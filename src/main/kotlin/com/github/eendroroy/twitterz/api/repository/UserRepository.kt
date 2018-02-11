package com.github.eendroroy.twitterz.api.repository

import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author indrajit
 */

@Repository("userRepository")
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun findByAccessToken(accessToken: String): User?
}

package com.github.eendroroy.twitterz.api.repository

import com.github.eendroroy.twitterz.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author indrajit
 */

@Repository('userRepository')
interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll()

    User findByEmail(String email)

    User findByAccessToken(String accessToken)
}

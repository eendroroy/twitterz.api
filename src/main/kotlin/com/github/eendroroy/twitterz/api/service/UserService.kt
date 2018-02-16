package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.User

/**
 *
 * @author indrajit
 */

interface UserService {
    fun allUsers(): List<User>?

    fun findUserById(userId: Long): User?

    fun findUserByEmail(email: String): User?

    fun saveUser(user: User): User?

    fun findUserByToken(token: String): User?
}

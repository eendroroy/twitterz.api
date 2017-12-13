package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.User

/**
 *
 * @author indrajit
 */

interface UserService {
    List<User> allUsers()

    User findUserByEmail(String email)

    User findUserByUserName(String userName)

    User saveUser(User user)

    User findUserByToken(String token)
}

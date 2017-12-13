package com.github.eendroroy.twitterz.api.service.impl

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 *
 * @author indrajit
 */

@Service('userService')
class UserServiceImpl implements UserService {

    @Qualifier('userRepository')
    @Autowired
    private UserRepository userRepository

    @Qualifier('encoder')
    @Autowired
    PasswordEncoder.Encoder passwordEncoder

    @Override
    List<User> allUsers() {
        userRepository.findAll()
    }

    @Override
    User findUserByEmail(String email) {
        userRepository.findByEmail(email)
    }

    @Override
    User findUserByUserName(String userName) {
        userRepository.findByUserName(userName)
    }

    @Override
    User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.password))
        user.setActive(1)
        userRepository.save(user)
    }

    @Override
    User findUserByToken(String token) {
        userRepository.findByAccessToken(token)
    }
}

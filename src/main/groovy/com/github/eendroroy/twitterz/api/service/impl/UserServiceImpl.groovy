package com.github.eendroroy.twitterz.api.service.impl

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 *
 * @author indrajit
 */

@Service("userService")
class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private transient UserRepository userRepository

    @Qualifier("encoder")
    @Autowired
    PasswordEncoder.Encoder passwordEncoder

    @Override
    public List<User> allUsers() {
        return userRepository.findAll()
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()))
        user.setActive(1)
        return userRepository.save(user)
    }

    @Override
    public User currentUser() {
        // TODO: implement
        return null
    }

    @Override
    public User findUserByToken(String token) {
        return userRepository.findByAccessToken(token)
    }
}

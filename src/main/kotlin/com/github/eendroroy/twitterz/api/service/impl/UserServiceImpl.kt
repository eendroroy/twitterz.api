package com.github.eendroroy.twitterz.api.service.impl

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

/**
 *
 * @author indrajit
 */

@Service("userService")
@Transactional
class UserServiceImpl : UserService {
    @Qualifier("userRepository")
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun allUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun findUserById(userId: Long): User? {
        val user: Optional<User> = userRepository.findById(userId)
        if (user.isPresent) return user.get()
        return null
    }

    override fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun findUserByUserName(userName: String): User? {
        return userRepository.findByUserName(userName)
    }

    override fun saveUser(user: User): User? {
        return userRepository.save(user)
    }

    override fun findUserByToken(token: String): User? {
        return userRepository.findByAccessToken(token)
    }
}

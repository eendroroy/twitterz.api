package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

/**
 *
 * @author indrajit
 */

@SpringBootTest(webEnvironment = NONE)
class UserServiceSpec extends Specification {
    @Autowired private UserService userService
    @Autowired private UserRepository userRepository

    def setup() {
        userRepository.deleteAll()
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def 'initialUsersCountIsZero'() {
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }

    def 'testCreateUser'() {
        when:
            User user1 = new User()
            user1.userName = 'user1'
            user1.password = 'password1'
            user1.email = 'user1@example.com'

            User user2 = new User()
            user2.userName = 'user2'
            user2.password = 'password2'
            user2.email = 'user2@example.com'

        then:
            assertThat(userService.saveUser(user1)).isNotEqualTo(null)
            assertThat(userService.allUsers().size()).isEqualTo(1)

            assertThat(userService.saveUser(user2)).isNotEqualTo(null)
            assertThat(userService.allUsers().size()).isEqualTo(2)
    }

    def 'testFindUserByEmail'() {
        when:
            User user3 = new User()
            user3.userName = 'user3'
            user3.password = 'password3'
            user3.email = 'user3@example.com'

            User user4 = new User()
            user4.userName = 'user4'
            user4.password = 'password4'
            user4.email = 'user4@example.com'

        then:
            assertThat(userService.saveUser(user3)).isNotEqualTo(null)
            assertThat(userService.saveUser(user4)).isNotEqualTo(null)
            assertThat(userService.allUsers().size()).isEqualTo(2)
            assertThat(userService.findUserByEmail('user3@example.com')).isEqualToComparingFieldByField(user3)
            assertThat(userService.findUserByEmail('user3@example.com')).isNotEqualTo(user4)
            assertThat(userService.findUserByEmail('user4@example.com')).isEqualToComparingFieldByField(user4)
            assertThat(userService.findUserByEmail('user4@example.com')).isNotEqualTo(user3)
    }
}
package com.github.eendroroy.twitterz.api.service

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 *
 * @author indrajit
 */

@SpringBootTest(webEnvironment = NONE)
class UserServiceSpec extends Specification {
    private static final USERDATA_1 = [email:'dummy1@example.com', userName:'dummy1', password:'dummy1@password',]
    private static final USERDATA_2 = [email:'dummy3@example.com', userName:'dummy3', password:'dummy3@password',]
    private static final USERDATA_3 = [email:'dummy4@example.com', userName:'dummy4', password:'dummy4@password',]

    @Autowired private UserService userService
    @Autowired private UserRepository userRepository

    void setup() {
        userRepository.deleteAll()
    }

    void cleanup() {
        userRepository.deleteAll()
    }

    void 'initialUsersCountIsZero'() {
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }

    void 'testCreateUser'() {
        when:
            User user1 = new User()
            user1.email = USERDATA_1.email
            user1.userName = USERDATA_1.userName
            user1.password = USERDATA_1.password

        then:
            assertThat(userService.saveUser(user1)).isNotEqualTo(null)
            assertThat(userService.allUsers().size()).isEqualTo(1)
    }

    void 'testFindUserByEmail'() {
        when:
            User user2 = new User()
            user2.email = USERDATA_2.email
            user2.userName = USERDATA_2.userName
            user2.password = USERDATA_2.password

            User user3 = new User()
            user3.email = USERDATA_3.email
            user3.userName = USERDATA_3.userName
            user3.password = USERDATA_3.password

        then:
            assertThat(userService.saveUser(user2)).isNotEqualTo(null)
            assertThat(userService.saveUser(user3)).isNotEqualTo(null)
            assertThat(userService.allUsers().size()).isEqualTo(2)
            assertThat(userService.findUserByEmail(user2.email)).isEqualToComparingFieldByField(user2)
            assertThat(userService.findUserByEmail(user2.email)).isNotEqualTo(user3)
            assertThat(userService.findUserByEmail(user3.email)).isEqualToComparingFieldByField(user3)
            assertThat(userService.findUserByEmail(user3.email)).isNotEqualTo(user2)
    }
}

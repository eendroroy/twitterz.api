package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat
/**
 *
 * @author indrajit
 */

@RunWith(SpringRunner)
@SpringBootTest
class UserServiceTest {
    @Autowired UserService userService
    @Autowired UserRepository userRepository

    @Test
    void initialUsersCountIsZero() throws Exception {
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }

    @Test
    void createUser() throws Exception {
        User user1 = new User()
        user1.userName = 'user1'
        user1.password = 'password'
        user1.email = 'user1@example.com'

        User user2 = new User()
        user2.userName = 'user2'
        user2.password = 'password'
        user2.email = 'user2@example.com'

        assertThat(userService.saveUser(user1)).isNotEqualTo(null)
        assertThat(userService.allUsers().size()).isEqualTo(1)

        assertThat(userService.saveUser(user2)).isNotEqualTo(null)
        assertThat(userService.allUsers().size()).isEqualTo(2)

        userRepository.deleteAll()
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }

    @Test
    void findUserByEmail() throws Exception {
        User user1 = new User()
        user1.userName = 'user1'
        user1.password = 'password'
        user1.email = 'user1@example.com'

        User user2 = new User()
        user2.userName = 'user2'
        user2.password = 'password'
        user2.email = 'user2@example.com'

        assertThat(userService.saveUser(user1)).isNotEqualTo(null)
        assertThat(userService.saveUser(user2)).isNotEqualTo(null)
        assertThat(userService.allUsers().size()).isEqualTo(2)

        assertThat(userService.findUserByEmail('user1@example.com')).isEqualToComparingFieldByField(user1)
        assertThat(userService.findUserByEmail('user2@example.com')).isEqualToComparingFieldByField(user2)

        assertThat(userService.findUserByEmail('user1@example.com')).isNotEqualTo(user2)
        assertThat(userService.findUserByEmail('user2@example.com')).isNotEqualTo(user1)

        userRepository.deleteAll()
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }
}

package com.github.eendroroy.twitterz.api

import com.github.eendroroy.twitterz.api.service.UserService
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
class TZApplicationTests {

    @Autowired private UserService userService

    @Test
    void exampleTest() {
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }
}

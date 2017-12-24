package com.github.eendroroy.twitterz.api

import com.github.eendroroy.twitterz.api.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner)
@SpringBootTest
class TZApplicationTests {

    @Autowired UserService userService

	@Test
    void exampleTest() throws Exception {
        assertThat(userService.allUsers().size()).isEqualTo(0)
    }
}

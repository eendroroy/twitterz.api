package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.TestUtil
import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
/**
 *
 * @author indrajit
 */

@SpringBootTest(webEnvironment = NONE)
class UserControllerSpec extends Specification {
    @InjectMocks
    private UserController userController

    private MockMvc mockMvc

    @Spy
    private UserService userService

    @Spy
    private PasswordEncoder passwordEncoder

    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = standaloneSetup(userController).build()

    }

    def '/register should give bad request for empty body'() {
        expect:
            mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
    }

    def 'register new user should pass'() {
        given:
            User user = new User()
            user.email = 'dummy1@example.com'
            user.userName = 'dummy1'
            user.password = 'dummy@password'

        expect:
            mockMvc.perform(
                    post("/user/register")
                            .content(TestUtil.convertObjectToJsonBytes(user))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }

    def 'register existing username should fail'() {

        given:
            User user = new User()
            user.email = 'dummy1@example.com'
            user.userName = 'dummy1'
            user.password = 'dummy@password'


            User user2 = new User()
            user2.email = 'dummy1_new@example.com'
            user2.userName = 'dummy1'
            user2.password = 'dummy_new@password'

        when:
            userService.saveUser(user)

        then:
            mockMvc.perform(
                    post("/user/register")
                            .content(TestUtil.convertObjectToJsonBytes(user2))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }

    def 'register existing email should fail'() {

        given:
            User user = new User()
            user.email = 'dummy1@example.com'
            user.userName = 'dummy1'
            user.password = 'dummy@password'

            User user2 = new User()
            user2.email = 'dummy1@example.com'
            user2.userName = 'dummy1_new'
            user2.password = 'dummy_new@password'

        when:
            userService.saveUser(user)

        then:
            mockMvc.perform(
                    post("/user/register")
                            .content(TestUtil.convertObjectToJsonBytes(user2))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }
}

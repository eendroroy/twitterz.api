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

@SuppressWarnings('UnusedPrivateField')
@SpringBootTest(webEnvironment = NONE)
class UserControllerSpec extends Specification {
    private static final REGISTER_PATH_NAME = '/user/register'
    private static final USERDATA_1 = [email:'dummy1@example.com', userName:'dummy1', password:'dummy1@password',]
    private static final USERDATA_2 = [email:'dummy2@example.com', userName:'dummy2', password:'dummy2@password',]

    @InjectMocks
    private UserController userController

    private MockMvc mockMvc

    @Spy
    private UserService userService

    @Spy
    private PasswordEncoder passwordEncoder

    void setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = standaloneSetup(userController).build()

    }

    void 'register should give bad request for empty body'() {
        expect:
            mockMvc.perform(post(REGISTER_PATH_NAME).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
    }

    void 'register new user should pass'() {
        given:
            User user = new User()
            user.email = USERDATA_1.email
            user.userName = USERDATA_1.userName
            user.password = USERDATA_1.password

        expect:
            mockMvc.perform(
                    post(REGISTER_PATH_NAME)
                            .content(TestUtil.convertObjectToJsonBytes(user))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }

    void 'register existing username should fail'() {

        given:
            User user = new User()
            user.email = USERDATA_1.email
            user.userName = USERDATA_1.userName
            user.password = USERDATA_1.password

            User user2 = new User()
            user2.email = USERDATA_2.email
            user2.userName = USERDATA_2.userName
            user2.password = USERDATA_2.password

        when:
            userService.saveUser(user)

        then:
            mockMvc.perform(
                    post(REGISTER_PATH_NAME)
                            .content(TestUtil.convertObjectToJsonBytes(user2))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }

    void 'register existing email should fail'() {

        given:
            User user = new User()
            user.email = USERDATA_1.email
            user.userName = USERDATA_1.userName
            user.password = USERDATA_1.password

            User user2 = new User()
            user2.email = USERDATA_2.email
            user2.userName = USERDATA_2.userName
            user2.password = USERDATA_2.password

        when:
            userService.saveUser(user)

        then:
            mockMvc.perform(
                    post(REGISTER_PATH_NAME)
                            .content(TestUtil.convertObjectToJsonBytes(user2))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }
}

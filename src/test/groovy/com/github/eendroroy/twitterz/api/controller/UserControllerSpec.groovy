package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.TestUtil
import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.service.UserService
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes
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

    def 'testRegisterURL'() {

        when:
            User user = new User()
            user.email = 'dummy1@example.com'
            user.userName = 'dummy1'
            user.password = 'dummy@password'

        then:
            mockMvc.perform(post("/user/register").accept(MediaType.APPLICATION_JSON))
            mockMvc.perform(post("/user/register").accept(MediaTypes.HAL_JSON_VALUE))

            mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())

            mockMvc.perform(
                    post("/user/register")
                            .content(TestUtil.convertObjectToJsonBytes(user))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andExpect(status().isOk())
    }
}

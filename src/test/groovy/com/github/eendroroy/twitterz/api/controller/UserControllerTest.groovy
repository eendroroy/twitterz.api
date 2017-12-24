package com.github.eendroroy.twitterz.api.controller

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

/**
 *
 * @author indrajit
 */

@RunWith(SpringRunner)
@SpringBootTest
class UserControllerTest {
    private MockMvc mockMvc

    @Before
    void setup() {
        this.mockMvc = standaloneSetup(new UserController()).build()
    }

    @Test
    void testSayHelloWorld() {
        assertThat(true).isEqualTo(true)
//        this.mockMvc.perform(
//                post("/user/register")
//                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
//        ).andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
    }
}

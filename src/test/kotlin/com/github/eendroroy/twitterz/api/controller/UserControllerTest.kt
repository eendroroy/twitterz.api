package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
import com.github.eendroroy.twitterz.api.resource.UserResource
import com.github.eendroroy.twitterz.api.security.PasswordEncoder
import com.github.eendroroy.twitterz.api.test.helper.BaseTester
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest : BaseTester() {
    @Autowired
    private lateinit var template: TestRestTemplate

    @Qualifier("userRepository")
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Before
    @Throws(Exception::class)
    fun setup() {
        val user = User()
        user.userName = "user0"
        user.fullName = "user0"
        user.email = "user0@example.com"
        user.password = passwordEncoder.encode("user0password")
        user.active = 1
        userRepository.save(user)
    }

    @After
    @Throws(Exception::class)
    fun cleanup() {
        userRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun testUserShouldBeCreated() {
        val id = (0..1000).shuffled().last()
        val user = getHttpEntity("{\"user\": {" +
                "\"userName\": \"user$id\"," +
                "\"fullName\": \"user$id\"," +
                "\"email\": \"u$id@e.c\"," +
                "\"password\": \"password\"" +
                "}}")
        val resultAsset = template.postForEntity("/users/register", user, UserResource::class.java)
        Assert.assertNotNull(resultAsset.body!!.user)
        Assert.assertNotNull(resultAsset.body!!.user!!.id)
        Assert.assertEquals(resultAsset.body!!.user!!.userName, "user$id")
        Assert.assertEquals(resultAsset.body!!.user!!.fullName, "user$id")
        Assert.assertEquals(resultAsset.body!!.user!!.active, 1)
    }

    @Test
    @Throws(Exception::class)
    fun testRegisterShouldReturnNotAcceptable() {
        val user = getHttpEntity("{\"user\": {" +
                "\"userName\": \"user0\"," +
                "\"fullName\": \"user0\"," +
                "\"email\": \"user0@example.com\"," +
                "\"password\": \"password\"" +
                "}}")
        val resultAsset = template.postForEntity("/users/register", user, UserResource::class.java)
        Assert.assertNotNull(resultAsset.body!!.message)
        Assert.assertEquals(resultAsset.statusCodeValue, HttpStatus.NOT_ACCEPTABLE.value())
    }

    @Test
    @Throws(Exception::class)
    fun testRegisterShouldReturnUnProcessableEntity() {
        val user = getHttpEntity("{\"user\": {}}")
        val resultAsset = template.postForEntity("/users/register", user, UserResource::class.java)
        Assert.assertEquals(resultAsset.statusCodeValue, HttpStatus.UNPROCESSABLE_ENTITY.value())
    }
}
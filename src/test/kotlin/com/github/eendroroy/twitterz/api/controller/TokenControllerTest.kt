package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.repository.UserRepository
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
class TokenControllerTest : BaseTester() {
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
    fun testShouldCreateToken() {
        val request = getHttpEntity("{\"email\": \"user0@example.com\", \"password\": \"user0password\"}")
        val resultAsset = template.postForEntity("/token/create", request, Map::class.java)
        Assert.assertEquals(resultAsset.statusCodeValue, HttpStatus.OK.value())
        Assert.assertEquals(resultAsset.body!!.get("success"), true)
        Assert.assertNotEquals(resultAsset.body!!.get("token").toString().length, 0)
        Assert.assertEquals(
                resultAsset.body!!.get("token").toString(),
                userRepository.findByEmail("user0@example.com")!!.accessToken
        )
        Assert.assertEquals(resultAsset.body!!.get("message"), null)
    }

    @Test
    @Throws(Exception::class)
    fun testShouldNotFindUser() {
        val request = getHttpEntity("{\"email\": \"user@example.com\", \"password\": \"user0password\"}")
        val resultAsset = template.postForEntity("/token/create", request, Map::class.java)
        Assert.assertEquals(resultAsset.statusCodeValue, HttpStatus.UNPROCESSABLE_ENTITY.value())
        Assert.assertEquals(resultAsset.body!!.get("success"), false)
        Assert.assertEquals(resultAsset.body!!.get("token"), null)
        Assert.assertEquals(resultAsset.body!!.get("message"), "user not found")
    }

    @Test
    @Throws(Exception::class)
    fun testShouldNotCreateToken() {
        val request = getHttpEntity("{\"email\": \"user0@example.com\", \"password\": \"user_password\"}")
        val resultAsset = template.postForEntity("/token/create", request, Map::class.java)
        Assert.assertEquals(resultAsset.statusCodeValue, HttpStatus.UNPROCESSABLE_ENTITY.value())
        Assert.assertEquals(resultAsset.body!!.get("success"), false)
        Assert.assertEquals(resultAsset.body!!.get("token"), null)
        Assert.assertEquals(resultAsset.body!!.get("message"), "password did not match")
    }
}
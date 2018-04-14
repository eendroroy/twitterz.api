package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.repository.UserRepository
import com.github.eendroroy.twitterz.api.resource.UserResource
import com.github.eendroroy.twitterz.api.test.helper.BaseTester
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest : BaseTester(){
    @Autowired
    private lateinit var template: TestRestTemplate

    @Qualifier("userRepository")
    @Autowired
    private lateinit var userRepository: UserRepository

    @Before
    @Throws(Exception::class)
    fun setup() {}

    @After
    @Throws(Exception::class)
    fun cleanup() {}

    companion object {

        @JvmStatic
        @BeforeClass
        @Throws(Exception::class)
        fun setupAll() {}

        @JvmStatic
        @AfterClass
        @Throws(Exception::class)
        fun cleanupAll() {
//            UserControllerTest().userRepository.deleteAll()
        }
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
}
package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.test.helper.BaseTester
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class DummyControllerTest : BaseTester() {
    @Autowired
    private val template: TestRestTemplate? = null

    @Before
    @Throws(Exception::class)
    fun setup() {

    }

    @Test
    @Throws(Exception::class)
    fun testArticleShouldBeCreated() {
        val resultAsset = template!!.getForEntity("/dummy", Map::class.java)
        Assert.assertNotNull(resultAsset.body!!["success"])
        Assert.assertEquals(resultAsset.body!!["success"], true)
    }
}
package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.Tweet
import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.service.TweetService
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.text.ParseException

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = ["/tweet"])
class TweetController {
    @Autowired
    private lateinit var tweetService: TweetService

    @Autowired
    private lateinit var userService: UserService

    @RequestMapping(
            path = [""], method = [RequestMethod.GET],
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun getAllTweet(): Map<String, Any?> {
        val tweets: List<Tweet> = tweetService.allTweets()!!
        return mapOf("count" to tweets.size, "_embedded" to mapOf<Any, Any>("tweets" to tweets))
    }

    @RequestMapping(
            path = [""], method = [RequestMethod.POST],
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun addTweet(
            @RequestBody tweet: Tweet?, request: HttpServletRequest, response: HttpServletResponse
    ) : Map<String, Any?> {
        return try {
            val user: User = userService.findUserByToken(request.getHeader("token"))!!
            tweet!!.user = user
            tweetService.saveTweet(tweet)
            mapOf("Success" to true, "_embedded" to mapOf<Any, Any>("tweet" to tweet))
        } catch (exception: DataIntegrityViolationException) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            mapOf("success" to false, "details" to exception.message, "_embedded" to mapOf("tweet" to tweet))
        }
    }
}

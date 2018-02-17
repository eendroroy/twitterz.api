package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.Tweet
import com.github.eendroroy.twitterz.api.entity.User
import com.github.eendroroy.twitterz.api.resource.TweetResource
import com.github.eendroroy.twitterz.api.service.TweetService
import com.github.eendroroy.twitterz.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(
        path = ["tweet"],
        consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class TweetController {
    @Autowired
    private lateinit var tweetService: TweetService

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("")
    @ResponseBody
    fun getAllTweet(): ResponseEntity<Resources<TweetResource>> {
        val tweets: List<Tweet> = tweetService.allTweets()!!
        val resources: Resources<TweetResource> = Resources(tweets.map { TweetResource(it) })
        return ok(resources)
    }

    @GetMapping("{tweetId}")
    @ResponseBody
    fun getTweetById(
            @PathVariable("tweetId") tweetId: Long,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ResponseEntity<Resource<TweetResource>> {
        val tweet: Tweet? = tweetService.findTweetById(tweetId) ?: throw Exception()
        val resources: Resource<TweetResource> = Resource(TweetResource(tweet!!))
        return ok(resources)
    }

    @DeleteMapping("{tweetId}")
    @ResponseBody
    fun deleteTweetById(
            @PathVariable("tweetId") tweetId: Long,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): Map<String, Any?> {
        val user: User = userService.findUserByToken(request.getHeader("token"))!!
        val tweet: Tweet = user.tweets.find { it!!.id == tweetId } ?: return mapOf(
                "Success" to false,
                "details" to "Tweet not found by id {$tweetId}"
        )
        tweetService.deleteTweet(tweet)
        return mapOf(
                "Success" to true,
                "details" to "Tweet deleted with id {$tweetId}"
        )
    }

    @PostMapping("")
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

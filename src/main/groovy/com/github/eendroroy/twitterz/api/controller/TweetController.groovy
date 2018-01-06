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
@RequestMapping(path = '/tweet')
class TweetController {
    @Autowired
    private TweetService tweetService

    @Autowired
    private UserService userService

    @RequestMapping(
            path = '', method = RequestMethod.GET,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    Map<Object, Object> getAllTweet() {
        List<Tweet> tweets = tweetService.allTweets()
        [count:tweets.size(), _embedded:[tweets:tweets],]
    }

    @RequestMapping(
            path = '', method = RequestMethod.POST,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    Map<Object, Object> create(
            @RequestBody Tweet tweet, HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        try {
            User user = userService.findUserByToken(request.getHeader('token'))
            tweet.user = user
            tweetService.saveTweet(tweet)
            [success:true, _embedded:[tweet:tweet],]
        } catch (DataIntegrityViolationException exception) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            [success:false, details:exception.message, _embedded:[tweet:tweet],]
        }
    }
}

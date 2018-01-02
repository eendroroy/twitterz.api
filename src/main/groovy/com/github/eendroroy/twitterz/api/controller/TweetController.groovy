package com.github.eendroroy.twitterz.api.controller

import com.github.eendroroy.twitterz.api.entity.Tweet
import com.github.eendroroy.twitterz.api.service.TweetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = '/tweet')
class TweetController {
    @Autowired
    private TweetService tweetService

    @RequestMapping(
            path = '', method = RequestMethod.GET,
            consumes = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,],
            produces = [MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,]
    )
    @ResponseBody
    List<Tweet> getAllTweet() {
        tweetService.allTweets()
    }
}

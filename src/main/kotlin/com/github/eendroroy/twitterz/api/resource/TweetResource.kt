package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.entity.Tweet
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

/**
 *
 * @author indrajit
 */

@Relation(collectionRelation = "tweets")
class TweetResource() : ResourceSupport() {

    var tweet: Tweet? = null

    constructor(tweet: Tweet?) : this() {
        this.tweet = tweet
    }

    constructor(tweetResource: TweetResource?) : this() {
        this.tweet = tweetResource!!.tweet
    }
}
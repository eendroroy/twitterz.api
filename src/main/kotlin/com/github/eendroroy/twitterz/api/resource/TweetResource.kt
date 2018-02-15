package com.github.eendroroy.twitterz.api.resource

import com.github.eendroroy.twitterz.api.entity.Tweet
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

/**
 *
 * @author indrajit
 */

@Relation(collectionRelation = "tweets")
class TweetResource(tweet: Tweet) : ResourceSupport() {

    var tweet: Tweet? = null

    init {
        this.tweet = tweet
    }

}
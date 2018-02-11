package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.Tweet

/**
 *
 * @author indrajit
 */

interface TweetService {
    fun allTweets(): List<Tweet>?

    fun findTweetById(id: Long): Tweet?

    fun findTweetsByUserId(userId: Long): List<Tweet>?

    fun saveTweet(tweet: Tweet): Tweet?
}

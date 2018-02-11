package com.github.eendroroy.twitterz.api.repository

import com.github.eendroroy.twitterz.api.entity.Tweet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author indrajit
 */

@Repository("tweetRepository")
interface TweetRepository : JpaRepository<Tweet, Long> {
    fun findTweetById(tweetId: Long): Tweet

    fun findTweetsByUserId(userId: Long): List<Tweet>
}

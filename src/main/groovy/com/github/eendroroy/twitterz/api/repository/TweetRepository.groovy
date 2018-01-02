package com.github.eendroroy.twitterz.api.repository

import com.github.eendroroy.twitterz.api.entity.Tweet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author indrajit
 */

@Repository('tweetRepository')
interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAll()

    Tweet findTweetById(Long tweetId)

    List<Tweet> findTweetsByUserId(Long userId)
}
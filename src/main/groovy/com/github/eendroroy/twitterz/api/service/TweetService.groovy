package com.github.eendroroy.twitterz.api.service

import com.github.eendroroy.twitterz.api.entity.Tweet

/**
 *
 * @author indrajit
 */

interface TweetService {
    List<Tweet> allTweets()

    Tweet findTweetById(Long id)

    List<Tweet> findTweetsByUserId(Long userId)
}

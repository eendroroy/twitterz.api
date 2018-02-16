package com.github.eendroroy.twitterz.api.service.impl

import com.github.eendroroy.twitterz.api.entity.Tweet
import com.github.eendroroy.twitterz.api.repository.TweetRepository
import com.github.eendroroy.twitterz.api.service.TweetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 *
 * @author indrajit
 */

@Service("tweetService")
class TweetServiceImpl : TweetService {
    @Qualifier("tweetRepository")
    @Autowired
    private lateinit var tweetRepository: TweetRepository

    override fun allTweets(): List<Tweet>? {
        return tweetRepository.findAll()
    }

    override fun findTweetById(id: Long): Tweet? {
        return tweetRepository.findTweetById(id)
    }

    override fun findTweetsByUserId(userId: Long): List<Tweet>? {
        return tweetRepository.findTweetsByUserId(userId)
    }

    override fun saveTweet(tweet: Tweet): Tweet? {
        return tweetRepository.save(tweet)
    }

    override fun deleteTweet(tweet: Tweet) {
        tweetRepository.delete(tweet)
    }
}

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

    @Override
    override fun allTweets(): List<Tweet>? {
        return tweetRepository.findAll()
    }

    @Override
    override fun findTweetById(id: Long): Tweet? {
        return tweetRepository.findTweetById(id)
    }

    @Override
    override fun findTweetsByUserId(userId: Long): List<Tweet>? {
        return tweetRepository.findTweetsByUserId(userId)
    }

    @Override
    override fun saveTweet(tweet: Tweet): Tweet? {
        return tweetRepository.save(tweet)
    }
}

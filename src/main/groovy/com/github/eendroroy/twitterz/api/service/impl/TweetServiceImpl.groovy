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

@Service('tweetService')
class TweetServiceImpl implements TweetService {

    @Qualifier('tweetRepository')
    @Autowired
    private TweetRepository tweetRepository

    @Override
    List<Tweet> allTweets() {
        tweetRepository.findAll()
    }

    @Override
    Tweet findTweetById(Long id) {
        tweetRepository.findTweetById(id)
    }

    @Override
    List<Tweet> findTweetsByUserId(Long userId) {
        tweetRepository.findTweetsByUserId(userId)
    }

    @Override
    Tweet saveTweet(Tweet tweet) {
        tweetRepository.save(tweet)
    }
}

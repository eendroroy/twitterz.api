package com.github.eendroroy.twitterz.api.utils

class APIPaths {
    companion object {
        const val USER_USER_ID = "userId"

        const val USERS_PATH = "users"
        const val USER_REGISTRATION_PATH = "register"
        const val USER_FOLLOWINGS_PATH = "followings"
        const val USER_FOLLOWERS_PATH = "followers"
        const val USER_FOLLOW_PATH = "{$USER_USER_ID}/follow"

        const val TWEET_ID = "tweetId"

        const val TWEET_COMMON_PATH = "tweets"
        const val TWEETS_PATH = ""
        const val TWEET_CREATE_PATH = ""
        const val TWEET_PATH = "{$TWEET_ID}"
    }
}
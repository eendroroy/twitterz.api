package com.github.eendroroy.twitterz.api.utils

class APIPaths {
    companion object {
        const val USER_USER_ID = "userId"

        const val USER_PATH = "user"
        const val USER_REGISTRATION_PATH = "register"
        const val USER_FOLLOWINGS_PATH = "followings"
        const val USER_FOLLOWERS_PATH = "followers"
        const val USER_FOLLOW_PATH = "follow/{$USER_USER_ID}"
    }
}
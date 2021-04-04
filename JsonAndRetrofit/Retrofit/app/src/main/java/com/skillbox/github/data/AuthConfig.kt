package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "64c5e752de40c2e41749"
    const val CLIENT_SECRET = "73468ca2b6b1751a002abd35c4a4fc8b93b1b48a"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}
package com.example.makenotess.api

import com.example.makenotess.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


//Retrofit jo hai BTS okHttp interceptor ka use krta hai
//Interceptor-->Request ko send krne se pahle ye interceptor help krta hai header ko add krne me
class AuthInceptor @Inject constructor():Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {

        val request=chain.request().newBuilder()
        val token=tokenManager.getToken()
        request.addHeader("Authorization","Bearer$token")

        return chain.proceed(request.build())
    }
}
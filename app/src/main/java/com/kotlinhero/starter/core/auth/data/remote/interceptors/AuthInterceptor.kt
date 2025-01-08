package com.kotlinhero.starter.core.auth.data.remote.interceptors

import com.kotlinhero.starter.core.auth.data.SessionManager
import com.kotlinhero.starter.core.foundation.utils.exceptions.FailedToRefreshTokenException
import com.kotlinhero.starter.core.foundation.utils.failures.Failure
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.HttpURLConnection

class AuthInterceptor : Interceptor, KoinComponent {

    private val sessionManager: SessionManager by inject()

    override fun intercept(chain: Chain): Response {
        val accessToken: String = sessionManager.accessToken.value ?: ""
        val requestWithAuthorization: Request = newRequestWithAccessToken(
            chain.request(),
            accessToken
        )
        val response = chain.proceed(requestWithAuthorization)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newAccessTokenResult = runBlocking { sessionManager.refreshToken(accessToken) }
            return newAccessTokenResult.fold(
                onLeft = { failure ->
                    val invalidRefreshToken = failure is Failure.ClientError &&
                            failure.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED
                    if (invalidRefreshToken) {
                        runBlocking { sessionManager.triggerForceLogout() }
                    }
                    throw FailedToRefreshTokenException()
                },
                onRight = { newAccessToken ->
                    chain.proceed(
                        newRequestWithAccessToken(
                            request = chain.request(),
                            accessToken = newAccessToken,
                            overrideHeader = true
                        )
                    )
                }
            )
        }
        return response
    }

    private fun newRequestWithAccessToken(
        request: Request,
        accessToken: String,
        overrideHeader: Boolean = false
    ): Request {
        // Check if the request already has the Authorization header
        if (request.header("Authorization") != null && !overrideHeader) {
            return request
        }
        return request.newBuilder()
            .removeHeader("Authorization")
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}
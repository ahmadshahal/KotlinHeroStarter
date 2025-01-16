package com.kotlinhero.starter.core.data.remote.interceptors

import com.kotlinhero.starter.core.utils.localization.LocalizationUtils
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

internal class LanguageInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val languageCode: String = LocalizationUtils.getSelectedLanguage().code
        val requestWithLanguageCode: Request = newRequestWithLanguageCode(
            chain.request(),
            languageCode,
        )
        return chain.proceed(requestWithLanguageCode)
    }

    private fun newRequestWithLanguageCode(
        request: Request,
        languageCode: String,
    ): Request {
        return request.newBuilder()
            .header("x-locale", languageCode)
            .build()
    }
}
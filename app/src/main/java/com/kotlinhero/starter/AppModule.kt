package com.kotlinhero.starter

import com.kotlinhero.starter.features.auth.data.remote.interceptors.AuthInterceptor
import okhttp3.Interceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named

@Module
@ComponentScan
class AppModule {

    @Factory
    @Named("OkHttpInterceptors")
    fun provideOkHttpInterceptors(): List<Interceptor> {
        return listOf(AuthInterceptor())
    }
}
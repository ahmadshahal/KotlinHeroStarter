package com.kotlinhero.starter.core.auth.domain.usecases

import com.kotlinhero.starter.core.auth.data.SessionManager
import com.kotlinhero.starter.core.auth.domain.entities.User
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetUserFlowUseCase(private val sessionManager: SessionManager) {
    operator fun invoke(): Flow<User> = sessionManager.userFlow
}
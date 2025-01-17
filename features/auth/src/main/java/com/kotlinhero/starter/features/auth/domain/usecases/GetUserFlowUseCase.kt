package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.data.SessionManager
import com.kotlinhero.starter.features.auth.domain.entities.User
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetUserFlowUseCase(private val sessionManager: SessionManager) {
    operator fun invoke(): Flow<User> = sessionManager.userFlow
}
package com.kotlinhero.starter.usecases

import com.kotlinhero.starter.core.auth.data.SessionManager
import com.kotlinhero.starter.domain.usecases.GetStartDestinationUseCase
import com.kotlinhero.starter.domain.usecases.StartDestinationResult
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetStartDestinationUseCaseTest {

    @Mock
    private lateinit var sessionManager: SessionManager

    private lateinit var getStartDestinationUseCase: GetStartDestinationUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getStartDestinationUseCase = GetStartDestinationUseCase(
            sessionManager = sessionManager
        )
    }

    @Test
    fun `test if user logged in MainScreen should be returned`() = runTest {
        // Given
        val mockIsLoggedInFlow = MutableStateFlow(true)
        whenever(sessionManager.isLoggedInFlow).thenReturn(mockIsLoggedInFlow)

        // When
        val result = getStartDestinationUseCase.invoke()

        // Then
        Assert.assertTrue(result is StartDestinationResult.Home)
    }

    @Test
    fun `test if user not logged in LoginScreen should be returned`() = runTest {
        // Given
        val mockIsLoggedInFlow = MutableStateFlow(false)
        whenever(sessionManager.isLoggedInFlow).thenReturn(mockIsLoggedInFlow)

        // When
        val result = getStartDestinationUseCase.invoke()

        // Then
        Assert.assertTrue(result is StartDestinationResult.Auth)
    }
}
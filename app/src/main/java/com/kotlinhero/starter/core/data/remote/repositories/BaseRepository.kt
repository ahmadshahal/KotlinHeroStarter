package com.kotlinhero.starter.core.data.remote.repositories

import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    /**
     * Executes a network request and handles exceptions, returning an Either result.
     * @param request The suspend function representing the network request.
     * @return Either a successful result (Right) or a Failure (Left).
     */
    suspend fun <T> safeApiCall(
        request: suspend () -> T
    ): Either<Failure, T> {
        return try {
            val result = request()
            Either.Right(result)
        } catch (e: io.ktor.client.network.sockets.SocketTimeoutException) {
            Either.Left(Failure.Timeout)
        } catch (e: io.ktor.client.plugins.HttpRequestTimeoutException) {
            Either.Left(Failure.Timeout)
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            Either.Left(Failure.ClientError(e.response.status.value, e.response.status.description))
        } catch (e: io.ktor.client.plugins.ServerResponseException) {
            // TODO: Parse the ErrorResponse
            Either.Left(Failure.ServerError(e.response.status.value, e.response.status.description))
        } catch (e: io.ktor.client.plugins.RedirectResponseException) {
            Either.Left(Failure.RedirectError(e.response.status.value, e.response.status.description))
        } catch (e: java.net.UnknownHostException) {
            Either.Left(Failure.NoInternetConnection)
        } catch (e: java.net.ConnectException) {
            Either.Left(Failure.ConnectionFailure)
        } catch (e: java.net.SocketException) {
            Either.Left(Failure.ConnectionLost)
        } catch (e: Exception) {
            Either.Left(Failure.UnknownFailure(e.message ?: "An unknown error occurred"))
        }
    }
}
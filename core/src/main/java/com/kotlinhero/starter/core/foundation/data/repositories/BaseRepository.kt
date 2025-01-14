package com.kotlinhero.starter.core.foundation.data.repositories

import com.kotlinhero.starter.core.foundation.utils.Either
import com.kotlinhero.starter.core.foundation.utils.failures.Failure

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
            // 400..499
            // TODO: Parse body based on needs,
            //  different error codes require different parsing (e.g. validation errors).
            Either.Left(Failure.ClientError(e.response.status.value, e.response.status.description))
        } catch (e: io.ktor.client.plugins.ServerResponseException) {
            // 500..599
            Either.Left(Failure.InternalServerError(e.response.status.value, e.response.status.description))
        } catch (e: io.ktor.client.plugins.RedirectResponseException) {
            // 300..399
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
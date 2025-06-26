package com.dino.order.corefeature.data.remote.util

import android.content.Context
import android.util.Log
import com.dino.order.R
import com.dino.order.corefeature.data.remote.ResponseToResultMapper
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.domain.model.asStringResourceContent
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.corefeature.util.RetryableException
import com.ramcosta.composedestinations.BuildConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

class HttpAPIUtil @Inject constructor(
    val sPrefManager: SPrefManager,
    val context: Context
) {


    suspend inline fun <ResultType, reified ResponseType : ResponseToResultMapper<ResultType>> callAPIWithErrorHandling(
        noinline callAPI: suspend () -> HttpResponse,
//        noinline callRefreshTokenAPI: suspend () -> String,
        needToUpdateToken: Boolean = false
    ): Resource<ResultType> {
        var loopRounds = 0

        return retry(times = 3, initialDelay = 1000, factor = 2.0) {
            loopRounds++

            try {
                val response = callAPI()
                when {
                    response.status.isSuccess() -> {
                        val body = response.body<ResponseType>()
                        Resource.Success(body.mapResponseToResult())
                    }

                    response.status == HttpStatusCode.Companion.BadRequest -> Resource.Error(
                        message = R.string.error_bad_request.asStringResourceContent()
                    )

                    response.status == HttpStatusCode.Companion.Unauthorized -> {
                        if (needToUpdateToken && loopRounds == 1) {
//                            val newToken = callRefreshTokenAPI()
//                            if (newToken.isNotEmpty()) {
//                                sPrefManager.setToken(newToken)
//                                throw RetryableException("Token refreshed, retrying request.")
//                            }
                        }
                        Resource.Error(
                            message = R.string.error_unauthorized.asStringResourceContent()
                        )
                    }

                    response.status == HttpStatusCode.Companion.Forbidden -> Resource.Error(
                        message = R.string.error_forbidden.asStringResourceContent()
                    )

                    response.status == HttpStatusCode.Companion.NotFound -> Resource.Error(
                        message = R.string.error_not_found.asStringResourceContent()
                    )

                    response.status == HttpStatusCode.Companion.Conflict -> Resource.Error(
                        message = R.string.error_conflict.asStringResourceContent()
                    )

                    response.status == HttpStatusCode.Companion.InternalServerError -> Resource.Error(
                        message = R.string.internal_server_error.asStringResourceContent()
                    )

                    else -> Resource.Error(
                        message = context.getString(
                            R.string.error_with_http_status_code,
                            response.status.value
                        ).asStringResourceContent()
                    )
                }

            } catch (e: UnresolvedAddressException) {
                throw RetryableException("Network error", e)
            } catch (e: IOException) {
                throw RetryableException("Network error", e)
            } catch (e: HttpRequestTimeoutException) {
                throw RetryableException("Timeout error", e)
            } catch (e: JsonConvertException) {
                if (BuildConfig.DEBUG) throw e
                Resource.Error(
                    message = R.string.error_convert_json_response.asStringResourceContent()
                )
            } catch (e: RetryableException) {
                throw e
            } catch (e: Exception) {
                Log.e("HttpAPIUtil", "Unhandled exception", e)
                Resource.Error(
                    message = e.message?.asStringResourceContent()
                        ?: R.string.unknown_error_occurred.asStringResourceContent()
                )
            }
        } ?: Resource.Error(
            message = R.string.error_retries_exceeded.asStringResourceContent()
        )
    }


    suspend fun <T> retry(
        times: Int,
        initialDelay: Long,
        factor: Double,
        block: suspend () -> T
    ): T? {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
//                Log.e("Retry", "Attempt failed, retrying in $currentDelay ms", e)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
        return try {
            block()
        } catch (e: Exception) {
            null
        }
    }
}
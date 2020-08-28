package com.bomb.common.net.exception

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException

object ExceptionHandle {

    fun handleException(e: Throwable?): ApiException {
        val ne: ApiException

        when (e) {
            is HttpException -> {
                ne = ApiException(Error.HTTP_ERROR, e)
            }
            is JsonParseException,
            is JSONException,
            is ParseException,
            is MalformedJsonException -> {
                ne = ApiException(Error.PARSE_ERROR, e)
            }
            is ConnectException -> {
                ne = ApiException(Error.NETWORD_ERROR, e)
            }
            is SSLException -> {
                ne = ApiException(Error.SSL_ERROR, e)
            }
            is SocketTimeoutException -> {
                ne = ApiException(Error.TIMEOUT_ERROR, e)
            }
            is UnknownHostException -> {
                ne = ApiException(Error.TIMEOUT_ERROR, e)
            }
            else -> {
                ne = if (!e?.message.isNullOrBlank()) ApiException(
                    1000,
                    e?.message!!,
                    e
                ) else ApiException(Error.UNKNOWN, e)
            }
        }
        return ne
    }
}
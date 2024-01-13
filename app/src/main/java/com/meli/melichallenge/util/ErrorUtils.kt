package com.meli.melichallenge.util


import com.meli.melichallenge.R
import com.meli.melichallenge.domain.model.ResultValue
import com.meli.melichallenge.domain.model.exception.ApiException
import java.net.UnknownHostException

fun ResultValue.Error.errorMessage(): BindingString {
    return this.exception.errorMessage()
}

fun Throwable.errorMessage(): BindingString {
    return when {
        this is ApiException && !message.isNullOrBlank() -> TextBindingString(this.message!!)
        this is UnknownHostException -> ResourceBindingString(R.string.error_network_connection)
        else -> ResourceBindingString(R.string.error_unknown)
    }
}

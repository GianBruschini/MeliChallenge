package com.meli.melichallenge.domain.model

/**
 * Generic sealed class to manage responses and statuses
 */

sealed class ResultValue<out T> {
    data class Success<T>(var data: T) : ResultValue<T>()
    data class Error(var exception: Throwable) : ResultValue<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is ResultValue.Success<*> && data != null

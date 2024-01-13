package com.meli.melichallenge.data.api

import com.meli.melichallenge.domain.model.ResultValue

import com.google.gson.Gson
import com.meli.melichallenge.data.api.model.response.NetworkErrorResponse
import com.meli.melichallenge.data.api.model.response.NetworkResponse
import com.meli.melichallenge.domain.model.exception.ApiException
import retrofit2.Response

/**
 * Base remote source class with error handling
 */
abstract class BaseRemoteDataSource constructor(
    private val gson: Gson,
) {
    protected suspend fun <T> getResponse(
        request: suspend () -> Response<NetworkResponse<T>>,
    ): ResultValue<List<T>> {
        return try {
            val response = request.invoke()
            if (response.isSuccessful) {
                ResultValue.Success(response.body()!!.results)
            } else {
                val error =
                    gson.fromJson(response.errorBody()?.string(), NetworkErrorResponse::class.java)
                val exception = ApiException(error.message ?: "")
                ResultValue.Error(exception)
            }
        } catch (e: Exception) {
            ResultValue.Error(e)
        }
    }
}


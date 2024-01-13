package com.meli.melichallenge.data.api.model.response

data class NetworkResponse<T>(
    val site_id: String,
    val country_default_time_zone: String,
    val paging: Paging,
    val results: List<T>,
    val sort: Sort,
    val available_sorts: List<Sort>,
    val filters: List<Filter>,
    val available_filters: List<Filter>,
    val pdp_tracking: PdpTracking
)


data class Sort(
    val id: String,
    val name: String
)

data class Filter(val id: String, val name: String)

data class PdpTracking(
    val group: Boolean,
    val product_info: List<Any>
)


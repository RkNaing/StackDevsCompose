package com.rahulkumar.stackdevs.data.remote

// https://api.stackexchange.com/2.3/users?page=1&pagesize=20&order=desc&sort=reputation&site=stackoverflow
const val DOMAIN = "https://api.stackexchange.com"
const val API_VERSION = 2.3
const val BASE_URL = "$DOMAIN/$API_VERSION/"

const val CACHE_SIZE: Long = 100 * 1024 * 1024
const val DEFAULT_PAGE_SIZE = 20

/**
 * Custom request header to indicate that a specific request should be cached.
 */
const val HEADER_CACHE = "App-Cache"

const val PATH_DEVS_LIST_ALL = "users?order=desc&sort=reputation&site=stackoverflow"


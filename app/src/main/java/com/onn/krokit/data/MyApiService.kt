package com.onn.krokit.data

import retrofit2.http.GET

interface MyApiService {

    @GET("TUSA.json")
    suspend fun getDataServer(): Contain
}
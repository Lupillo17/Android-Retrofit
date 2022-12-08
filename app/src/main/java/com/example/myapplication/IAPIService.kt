package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IAPIService {
    @GET
    suspend fun getDogsByBreed(@Url url:String): Response<DogsResponse>
}
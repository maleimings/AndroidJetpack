package com.example.androidstuff.net

import retrofit2.http.GET

interface RepositoriesApiService {
    @GET("repositories?q=mobile&sort=starts&page=1&per_page=20")
    suspend fun getRepositories() : RepositoriesResponse
}
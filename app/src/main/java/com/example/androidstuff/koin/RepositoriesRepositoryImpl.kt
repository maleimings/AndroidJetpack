package com.example.androidstuff.koin

import com.example.androidstuff.net.RepositoriesApiService
import com.example.androidstuff.net.Repository
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoriesRepositoryImpl() : RepositoriesRepository {

    private val repositoriesApiService: RepositoriesApiService

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okhttp = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/search/")
            .build()

        repositoriesApiService = retrofit.create(RepositoriesApiService::class.java)
    }

    override suspend fun getRepositories(): List<Repository> {
        return repositoriesApiService.getRepositories().repos
    }
}
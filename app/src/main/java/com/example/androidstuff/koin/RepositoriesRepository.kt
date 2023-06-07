package com.example.androidstuff.koin

import com.example.androidstuff.net.Repository

interface RepositoriesRepository {
    suspend fun getRepositories(page: Int) : List<Repository>
}
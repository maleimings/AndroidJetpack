package com.example.androidstuff.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidstuff.koin.RepositoriesRepository

class RepositoriesPagingSource(private val repositoryRepository: RepositoriesRepository) : PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        try {
            val nextPage = params.key ?: 1
            val repos = repositoryRepository.getRepositories(nextPage)

            return LoadResult.Page(data = repos, prevKey = if (nextPage == 1) null else nextPage - 1, nextKey = nextPage + 1)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
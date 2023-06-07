package com.example.androidstuff.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstuff.koin.RepositoriesRepository
import com.example.androidstuff.net.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoriesViewModel(private val repositoriesRepository: RepositoriesRepository) : ViewModel() {

    val repositories = mutableStateOf(emptyList<Repository>())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repositoriesRepository.getRepositories()
            withContext(Dispatchers.Main) {
                repositories.value = data
            }
        }
    }
}
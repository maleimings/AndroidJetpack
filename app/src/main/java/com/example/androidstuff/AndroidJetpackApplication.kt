package com.example.androidstuff

import android.app.Application
import com.example.androidstuff.viewmodel.RestaurantsViewModel
import com.example.androidstuff.koin.RestaurantRepository
import com.example.androidstuff.koin.RestaurantRepositoryImpl
import com.example.androidstuff.viewmodel.RestaurantDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidJetpackApplication : Application() {
    private val appModule = module {
        single<RestaurantRepository> { RestaurantRepositoryImpl() }
        viewModel { RestaurantsViewModel(get(), get()) }
        viewModel { RestaurantDetailsViewModel(get(), get()) }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidJetpackApplication)
            modules(appModule)
        }
    }
}
package com.example.spendingtrackerapp

import android.app.Application
import com.example.spendingtrackerapp.balance.di.balanceModule
import com.example.spendingtrackerapp.core.di.coreModule
import com.example.spendingtrackerapp.spending_overview.di.spendingOverviewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(coreModule, balanceModule, spendingOverviewModule)
        }
    }
}
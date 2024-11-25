package com.example.spendingtrackerapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.spendingtrackerapp.core.data.RoomSpendingDataSource
import com.example.spendingtrackerapp.core.data.local.SpendingDatabase
import com.example.spendingtrackerapp.core.data.repository.CoreRepositoryImpl
import com.example.spendingtrackerapp.core.domain.LocalSpendingDataSource
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "spending_database"
        ).build()
    }

    single { get<SpendingDatabase>().dao }

    single {
        androidApplication().getSharedPreferences(
            "spending_preferences", Context.MODE_PRIVATE
        )
    }

    singleOf(::RoomSpendingDataSource).bind<LocalSpendingDataSource>()
    singleOf(::CoreRepositoryImpl).bind<CoreRepository>()
}
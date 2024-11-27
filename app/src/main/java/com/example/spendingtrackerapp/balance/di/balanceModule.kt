package com.example.spendingtrackerapp.balance.di

import com.example.spendingtrackerapp.balance.presentation.BalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val balanceModule = module {
    viewModel { BalanceViewModel(get()) }
}
package com.example.spendingtrackerapp.spending_details.di

import com.example.spendingtrackerapp.spending_details.domain.UpsertSpendingUseCase
import com.example.spendingtrackerapp.spending_details.presentation.SpendingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val spendingDetailsModule = module {

    single { UpsertSpendingUseCase(get()) }

    viewModel { SpendingDetailsViewModel(get()) }
}
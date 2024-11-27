package com.example.spendingtrackerapp.spending_overview.presentation

sealed interface SpendingOverviewAction {
    data object LoadSpendingOverviewAndBalance: SpendingOverviewAction
    data class OnDateChange(val newDate: Int): SpendingOverviewAction
    data class OnDeleteSpending(val spendingId: Int): SpendingOverviewAction
}
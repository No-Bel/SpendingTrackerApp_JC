package com.example.spendingtrackerapp.spending_details.domain

import com.example.spendingtrackerapp.core.domain.LocalSpendingDataSource
import com.example.spendingtrackerapp.core.domain.Spending

class UpsertSpendingUseCase(
    private val spendingDataSource: LocalSpendingDataSource
) {
    suspend operator fun invoke(spending: Spending): Boolean {

        if (spending.name.isBlank()) {
            return false
        }

        if (spending.price < 0) {
            return false
        }

        if (spending.kilograms < 0) {
            return false
        }

        if (spending.quantity < 0) {
            return false
        }

        spendingDataSource.upsertSpending(spending)
        return true
    }
}
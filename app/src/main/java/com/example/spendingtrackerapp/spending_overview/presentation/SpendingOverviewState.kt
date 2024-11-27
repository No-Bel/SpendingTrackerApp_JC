package com.example.spendingtrackerapp.spending_overview.presentation

import com.example.spendingtrackerapp.core.domain.Spending
import java.time.ZonedDateTime

data class SpendingOverviewState(
    val spendingList: List<Spending> = emptyList(),
    val datedList: List<ZonedDateTime> = emptyList(),
    val balance: Double = 0.0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickedDropDownMenuVisible: Boolean = false
)

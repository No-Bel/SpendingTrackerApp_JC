package com.example.spendingtrackerapp.spending_overview.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendingtrackerapp.core.domain.LocalSpendingDataSource
import com.example.spendingtrackerapp.core.domain.Spending
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository
import com.example.spendingtrackerapp.spending_overview.presentation.util.randomColor
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SpendingOverviewViewModel(
    private val spendingDateSource: LocalSpendingDataSource,
    private val coreRepository: CoreRepository
): ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set


    fun onAction(action: SpendingOverviewAction) {
        when(action) {
            SpendingOverviewAction.LoadSpendingOverviewAndBalance -> {
                loadSpendingListAndBalance()
            }
            is SpendingOverviewAction.OnDateChange -> {

            }
            is SpendingOverviewAction.OnDeleteSpending -> {

            }
        }
    }

    private fun loadSpendingListAndBalance() {
        viewModelScope.launch {
            val allDated = spendingDateSource.getAllDates()

            state = state.copy(
                spendingList = getSpendingListByDate(
                    allDated.lastOrNull() ?: ZonedDateTime.now()
                ),
                balance = coreRepository.getBalance() - spendingDateSource.getSpendBalance(),
                pickedDate = allDated.lastOrNull() ?: ZonedDateTime.now(),
                datedList = allDated.reversed()
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending> =
        spendingDateSource
            .getSpendingByDate(date)
            .reversed()
            .map { it.copy(color = randomColor()) }
}
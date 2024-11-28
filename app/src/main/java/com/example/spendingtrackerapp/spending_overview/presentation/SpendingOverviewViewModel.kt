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
                val newDate = state.dateList[action.newDate]
                viewModelScope.launch {
                    state = state.copy(
                        pickedDate = newDate,
                        spendingList = getSpendingListByDate(newDate)
                    )
                }
            }
            is SpendingOverviewAction.OnDeleteSpending -> {
                viewModelScope.launch {
                    spendingDateSource.deleteSpending(action.spendingId)
                    state = state.copy(
                        spendingList = getSpendingListByDate(state.pickedDate),
                        dateList = spendingDateSource.getAllDates(),
                        balance = coreRepository.getBalance() - spendingDateSource.getSpendBalance()
                    )
                }
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
                dateList = allDated.reversed()
            )

            val dummy  = listOf(
                Spending(
                    price = 23.3,
                    name = "name",
                    kilograms = 24.4,
                    dateTimeUtc = ZonedDateTime.now(),
                    color = randomColor(),
                    quantity = 34.3,
                    spendingId = null
                ),
                Spending(
                    price = 23.3,
                    name = "name",
                    kilograms = 24.4,
                    dateTimeUtc = ZonedDateTime.now(),
                    color = randomColor(),
                    quantity = 34.3,
                    spendingId = null
                ),
                Spending(
                    price = 23.3,
                    name = "name",
                    kilograms = 24.4,
                    dateTimeUtc = ZonedDateTime.now(),
                    color = randomColor(),
                    quantity = 34.3,
                    spendingId = null
                ),
                Spending(
                    price = 23.3,
                    name = "name",
                    kilograms = 24.4,
                    dateTimeUtc = ZonedDateTime.now(),
                    color = randomColor(),
                    quantity = 34.3,
                    spendingId = null
                ),
                Spending(
                    price = 23.3,
                    name = "name",
                    kilograms = 24.4,
                    dateTimeUtc = ZonedDateTime.now(),
                    color = randomColor(),
                    quantity = 34.3,
                    spendingId = null
                )
            )


            state = state.copy(
                spendingList = dummy
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending> =
        spendingDateSource
            .getSpendingByDate(date)
            .reversed()
            .map { it.copy(color = randomColor()) }
}
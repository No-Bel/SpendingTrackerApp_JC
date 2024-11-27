package com.example.spendingtrackerapp.core.domain

import java.time.ZonedDateTime

interface LocalSpendingDataSource {

    suspend fun getAllSpending(): List<Spending>

    suspend fun getSpendingByDate(dateTimeUtc: ZonedDateTime): List<Spending>

    suspend fun getAllDates(): List<ZonedDateTime>

    suspend fun getSingleSpending(id: Int): Spending

    suspend fun upsertSpending(spending: Spending)

    suspend fun getSpendBalance(): Double

    suspend fun deleteSpending(id: Int)
}
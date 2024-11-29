package com.example.spendingtrackerapp.core.data

import com.example.spendingtrackerapp.core.data.local.SpendingDao
import com.example.spendingtrackerapp.core.data.mapper.toNewSpendingEntity
import com.example.spendingtrackerapp.core.data.mapper.toSpending
import com.example.spendingtrackerapp.core.domain.LocalSpendingDataSource
import com.example.spendingtrackerapp.core.domain.Spending
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class RoomSpendingDataSource(
    private val dao: SpendingDao
) : LocalSpendingDataSource {

    override suspend fun getAllSpending(): List<Spending> {
        return dao.getAllSpending().map { it.toSpending() }
    }

    override suspend fun getSpendingByDate(dateTimeUtc: ZonedDateTime): List<Spending> =
        dao.getAllSpending()
            .map { it.toSpending() }
            .filter { spending ->
                spending.dateTimeUtc.dayOfMonth == dateTimeUtc.dayOfMonth
                        && spending.dateTimeUtc.month == dateTimeUtc.month
                        && spending.dateTimeUtc.year == dateTimeUtc.year
            }


    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDated = mutableSetOf<LocalDate>()
        return dao.getAllDates()
            .map { Instant.parse(it).atZone(ZoneId.of("UTC")) }
            .filter {
                uniqueDated.add(it.toLocalDate())
            }
    }

    override suspend fun getSingleSpending(id: Int): Spending =
        dao.getSingleSpending(id).toSpending()


    override suspend fun upsertSpending(spending: Spending) =
        dao.upsertSpending(spending.toNewSpendingEntity())


    override suspend fun getSpendBalance(): Double =
        dao.getSpendBalance() ?: 0.0


    override suspend fun deleteSpending(id: Int) =
        dao.deleteSpending(id)

}
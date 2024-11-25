package com.example.spendingtrackerapp.core.data.repository

import android.content.SharedPreferences
import com.example.spendingtrackerapp.core.domain.repository.CoreRepository

class CoreRepositoryImpl(
    private val prefs: SharedPreferences
): CoreRepository {

    override suspend fun updateBalance(balance: Double) {
        prefs.edit()
            .putFloat(KEY_BALANCE, balance.toFloat())
            .apply()
    }

    override suspend fun getBalance(): Double =
        prefs.getFloat(KEY_BALANCE, 0f).toDouble()

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
    }
}